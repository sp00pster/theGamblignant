package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theGamblignant.VriskaMod;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class PanicPupaRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = VriskaMod.makeID("PanicPupaRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("panicpupa.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("panicpupa.png"));

    public PanicPupaRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PanicPupaRelic();
    }
}
