package theHuman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.powers.BurningPower;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.*;

public class FireArms extends CustomRelic {

    public static final String ID = HumanMod.makeID("FireArms");

    private static final Texture IMG =
        TextureLoader.getTexture(makeRelicPath("FireArms.png"));
    private static final Texture OUTLINE =
        TextureLoader.getTexture(makeRelicOutlinePath("FireArms_Outline.png"));
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":BurningWords");

    public FireArms() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        this.tips.add(new PowerTip(uiStrings.TEXT[0], uiStrings.TEXT[1]));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        flash();
        if (c.hasTag(HumanMod.HumanCardTags.SHOOTER_HUMAN)) {
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player,
                                               new BurningPower(m,
                                                                AbstractDungeon.player,
                                                                1)));
        }
    }
}
