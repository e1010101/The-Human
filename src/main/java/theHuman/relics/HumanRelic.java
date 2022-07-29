package theHuman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.HumanMod;
import theHuman.powers.HealthinessPower;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makeRelicOutlinePath;
import static theHuman.HumanMod.makeRelicPath;

public class HumanRelic extends CustomRelic {

    public static final String ID = HumanMod.makeID("HumanRelic");

    private static final Texture IMG =
        TextureLoader.getTexture(makeRelicPath("Human_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(
        makeRelicOutlinePath("Human_relic_Outline.png"));

    public HumanRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }

    @Override
    public void onEquip() {
    }

    @Override
    public void onUnequip() {
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                                 new HealthinessPower(AbstractDungeon.player,
                                                      AbstractDungeon.player,
                                                      5)));
        AbstractDungeon.actionManager.addToBottom(
            new GainBlockAction(AbstractDungeon.player, 5));
    }

    @Override
    public int changeNumberOfCardsInReward(int numberOfCards) {
        return 4;
    }

}
