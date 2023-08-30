package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.actions.RollNumberEffect;
import theGamblignant.actions.TimedVFXAction;
import theGamblignant.powers.CursePower;
import theGamblignant.powers.LuckPower;
import theGamblignant.powers.VimPower;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class ScratchedCoinRelic extends CustomRelic {

    public static final String ID = VriskaMod.makeID("ScratchedCoinRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public ScratchedCoinRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName()); //this is for testing purposes, you can remove this eventually

    public void onVictory() {
        //code largely copied from the roll function

        //counts for roll-modifying powers
        int luck = 0;
        int vim = 0;
        int curse = 0;

        int result;
        int totalResult = 0;
        AbstractPlayer p = AbstractDungeon.player;
        GameActionManager a = AbstractDungeon.actionManager;

        //check for all powers that directly modify luck, make them flash, and record their values. remove vim and reduce curse also.
        if (p.hasPower(LuckPower.POWER_ID)) {
            p.getPower(LuckPower.POWER_ID).flash();
            luck = p.getPower(LuckPower.POWER_ID).amount;
        }

        if (p.hasPower(VimPower.POWER_ID)) {
            p.getPower(VimPower.POWER_ID).flash();
            vim = p.getPower(VimPower.POWER_ID).amount;
            a.addToBottom(new RemoveSpecificPowerAction(p, p, VimPower.POWER_ID));
        }

        if (p.hasPower(CursePower.POWER_ID)) {
            p.getPower(CursePower.POWER_ID).flash();
            curse = p.getPower(CursePower.POWER_ID).amount;
            a.addToBottom(new ReducePowerAction(p, p, CursePower.POWER_ID, 1));
        }

        if (p.hasRelic(BionicBoundbeastRelic.ID)) {
            //flash?
            luck += 1;
        }

        logger.info("____________ SCRATCHED COIN ROLL START ____________");

        //this is when the random number is actually selected
        result = AbstractDungeon.cardRandomRng.random(1, 20);
        logger.info("rolled a " + result + " before luck");

        //the luck powers are applied to the random number, creating the final result of the roll
        result += luck;
        result += vim;
        result -= curse;
        logger.info(luck + " luck, " + vim + " vim, and " + curse + " curse make the result a " + result + " (before being clamped)");

        //clamp result so it doesnt exceed the bounds of the roll
        if (result > 20) {result = 20;}
        if (result < 1) {result = 1;}

        logger.info("thus, the result is " + result);

        //make the roll vfx
        if (result == 20) {
            AbstractDungeon.actionManager.addToBottom(new TimedVFXAction(new RollNumberEffect(p.dialogX + 25F, p.dialogY, 1, result + "!")));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TimedVFXAction(new RollNumberEffect(p.dialogX + 25F, p.dialogY, 1, Integer.toString(result))));
        }

        addToBot(new GainGoldAction(result));

        logger.info("_____________ SCRATCHED COIN ROLL END _____________ (result is "+result+")");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new ScratchedCoinRelic();
    }

}
