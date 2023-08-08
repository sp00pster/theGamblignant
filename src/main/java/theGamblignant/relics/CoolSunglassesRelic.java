package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theGamblignant.VriskaMod;
import theGamblignant.powers.LuckPower;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class CoolSunglassesRelic extends CustomRelic {

    public static final String ID = VriskaMod.makeID("CoolSunglassesRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public CoolSunglassesRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    private boolean firstturn = true;
    private boolean coolness = true;

    public void atPreBattle() {
        this.flash();
        firstturn = true;
        coolness = true;
        if (!this.pulse) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    public void atTurnStart() {
        if (coolness && !firstturn) {
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LuckPower(AbstractDungeon.player, 1)));
        } else {
            this.pulse = true;
            coolness = true;
        }

        firstturn = false;
    }

    public void wasHPLost(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
            this.pulse = false;
            coolness = false;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new CoolSunglassesRelic();
    }
}
