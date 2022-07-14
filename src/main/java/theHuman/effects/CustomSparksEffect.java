package theHuman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;

public class CustomSparksEffect extends AbstractGameEffect {
    private final float x;
    private final float y;

    public CustomSparksEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.duration = 0.4F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.clank(this.x, this.y);
            this.isDone = true;
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW,
                                            ScreenShake.ShakeDur.SHORT, false);
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }

    private void clank(float x, float y) {
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 30; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(
                    new UpgradeShineParticleEffect(
                        x + MathUtils.random(-10.0F, 10.0F) * Settings.scale,
                        y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
            }
        }
    }
}
