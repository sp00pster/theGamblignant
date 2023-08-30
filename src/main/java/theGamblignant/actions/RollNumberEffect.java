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
    private static final float EFFECT_DUR = 0.4F;
    private final float x;
    private float y;
    private static final float GRAVITY_Y;
    private final String msg;
    private final int rolls;
    private int timemult;
    private float scale = 1.7F;
    private boolean playedSound = false;

    public RollNumberEffect(float x, float y, int rollcount, String msg) {
        this.rolls = rollcount;
        this.timemult = 1; //this should be sqrt(rolls) but thats kind of a headache
        this.duration = 0.6F * timemult;
        this.startingDuration = 0.6F * timemult;
        this.x = x;
        this.y = y;
        this.msg = msg;
        this.color = new Color(0.5F, 0.8F, 1.0F, 1.0F);
    }

    public void update() {
        this.y += 3 * GRAVITY_Y * Gdx.graphics.getDeltaTime();
        super.update();
        if (!this.playedSound) {
            if (rolls > 3) {
                CardCrawlGame.sound.play("VriskaMod:DICE");
            } else {
                CardCrawlGame.sound.play("VriskaMod:MANYDICE");
            }
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
