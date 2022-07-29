package theHuman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.HumanMod;
import theHuman.actions.FindJunkAction;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makeRelicOutlinePath;
import static theHuman.HumanMod.makeRelicPath;

public class Environmentalist extends CustomRelic {

    public static final String ID = HumanMod.makeID("Environmentalist");

    private static final Texture IMG =
        TextureLoader.getTexture(makeRelicPath("Environmentalist.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(
        makeRelicOutlinePath("Environmentalist_Outline.png"));

    public Environmentalist() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new FindJunkAction(1));
    }
}
