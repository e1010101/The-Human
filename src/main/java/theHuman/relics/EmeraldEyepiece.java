package theHuman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makeRelicOutlinePath;
import static theHuman.HumanMod.makeRelicPath;

public class EmeraldEyepiece extends CustomRelic {

    public static final String ID = HumanMod.makeID("EmeraldEyepiece");

    private static final Texture IMG =
        TextureLoader.getTexture(makeRelicPath("EmeraldEyepiece.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(
        makeRelicOutlinePath("EmeraldEyepiece_Outline.png"));

    public EmeraldEyepiece() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onSpendGold() {
        if (AbstractDungeon.player.gold > 200) {
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(20));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(30));
        }
    }
}
