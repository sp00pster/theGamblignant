package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theGamblignant.VriskaMod;
import theGamblignant.powers.LuckPower;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class BrightPowderRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = VriskaMod.makeID("BrightPowderRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BrightPowderRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            this.addToBot(new RelicAboveCreatureAction(p, this));
            this.addToBot(new ApplyPowerAction(p, p, new LuckPower(p, 1), 1));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BrightPowderRelic();
    }

}
