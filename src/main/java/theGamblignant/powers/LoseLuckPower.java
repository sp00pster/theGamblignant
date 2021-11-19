package theGamblignant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makePowerPath;

public class LoseLuckPower extends AbstractPower implements CloneablePowerInterface {
    public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName()); //this is for testing purposes, you can remove this eventually

    public static final String POWER_ID = VriskaMod.makeID("LoseLuck");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("arrowloseluck_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("arrowloseluck_power32.png"));

    public LoseLuckPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, LoseLuckPower.POWER_ID));
        }
    }

    public void updateDescription() {this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];}

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new LuckPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, LoseLuckPower.POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseLuckPower(owner, amount);
    }

}
