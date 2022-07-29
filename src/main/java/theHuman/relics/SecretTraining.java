package theHuman.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHuman.HumanMod;
import theHuman.cards.tokens.StabbyStabby;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makeRelicOutlinePath;
import static theHuman.HumanMod.makeRelicPath;

public class SecretTraining extends CustomRelic implements ClickableRelic {

    public static final String ID = HumanMod.makeID("SecretTraining");

    private static final Texture IMG =
        TextureLoader.getTexture(makeRelicPath("SecretTraining.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(
        makeRelicOutlinePath("SecretTraining_Outline.png"));

    private boolean usedThisCombat = false;

    public SecretTraining() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        refreshTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        this.usedThisCombat = false;
        this.beginLongPulse();
    }

    @Override
    public void onRightClick() {
        if (!this.isObtained || this.usedThisCombat) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null &&
            (AbstractDungeon.getCurrRoom()).phase ==
            AbstractRoom.RoomPhase.COMBAT) {
            this.usedThisCombat = true;
            this.addToBot(new DrawCardAction(3));
            this.addToBot(new MakeTempCardInHandAction(new StabbyStabby()));
            this.stopPulse();
        }
    }

    public void refreshTips() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
        this.tips.add(new CardPowerTip(new StabbyStabby()));
    }
}
