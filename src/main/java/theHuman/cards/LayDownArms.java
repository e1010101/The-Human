package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import theHuman.HumanMod;
import theHuman.actions.LayDownArmsAction;
import theHuman.characters.TheHuman;
import theHuman.util.HumanUtils;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class LayDownArms extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(LayDownArms.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("LayDownArms.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":PacifistWords");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#34eb52]" + uiStrings.TEXT[0], uiStrings.TEXT[1]);
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public LayDownArms() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        magicNumber = baseMagicNumber = 3;
        purgeOnUse = true;
        this.tags.add(HumanMod.HumanCardTags.PACIFIST_HUMAN);
    }

    public boolean shouldGlowSpecial() {
        return !HumanUtils.checkHandForAttacks(AbstractDungeon.player);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LayDownArmsAction(this));
        if (!HumanUtils.checkHandForAttacks(p)) {
            this.addToBot(new DrawCardAction(5));
        } else {
            this.addToBot(new DrawCardAction(1));
        }
        if (upgraded) {
            this.addToBot(
                new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
            this.addToBot(
                new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = shouldGlowSpecial() ? Color.FOREST.cpy() :
                         AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
