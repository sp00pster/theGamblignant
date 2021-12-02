package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PenNibPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.powers.VimPower;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class TetradactylyRelic extends CustomRelic {

    public static final String ID = VriskaMod.makeID("TetradactylyRelic");
    public static final int COUNT = 8;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName()); //this is for testing purposes, you can remove this eventually


    public TetradactylyRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        ++this.counter;
        if (this.counter == 8) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VimPower(AbstractDungeon.player, 4), 4, true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


}
