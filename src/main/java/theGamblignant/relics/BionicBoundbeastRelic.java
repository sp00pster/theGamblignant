package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theGamblignant.VriskaMod;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class BionicBoundbeastRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = VriskaMod.makeID("BionicBoundbeastRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BionicBoundbeastRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BionicBoundbeastRelic();
    }
}
