//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theGamblignant.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

//this was built off of BlockedNumberEffect

public class RollNumberEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.3F;
    private float x;
    private float y;
    private static final float GRAVITY_Y;
    private String msg;
    private float scale = 1.5F;
    private boolean playedSound = false;

    public RollNumberEffect(float x, float y, String msg) {
        this.duration = 0.35F;
        this.startingDuration = 0.35F;
        this.x = x;
        this.y = y;
        this.msg = msg;
        this.color = new Color(0.5F, 0.8F, 1.0F, 1.0F);
    }

    public void update() {
        this.y += 4 * GRAVITY_Y * Gdx.graphics.getDeltaTime();
        super.update();
        this.color.a = this.duration / this.startingDuration;
        if (!this.playedSound) {
            CardCrawlGame.sound.play("VriskaMod:DICE");
            this.playedSound = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (this.scale <= 0.0F) {
            this.scale = 0.01F;
        }

        FontHelper.damageNumberFont.getData().setScale(this.scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, this.msg, this.x, this.y, this.color);
    }

    public void dispose() {
    }

    static {
        GRAVITY_Y = 75.0F * Settings.scale;
    }
}
