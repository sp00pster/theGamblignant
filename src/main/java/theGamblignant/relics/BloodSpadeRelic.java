package theGamblignant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theGamblignant.VriskaMod;
import theGamblignant.util.TextureLoader;

import static theGamblignant.VriskaMod.makeRelicOutlinePath;
import static theGamblignant.VriskaMod.makeRelicPath;

public class BloodSpadeRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = VriskaMod.makeID("BloodSpadeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BloodSpadeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    //code here

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BloodSpadeRelic();
    }

}
