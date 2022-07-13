package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.util.HumanUtils;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class MidlifeCrisis extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(MidlifeCrisis.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("MidlifeCrisis.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":PacifistWords");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#34eb52]" + uiStrings.TEXT[0], uiStrings.TEXT[1]);
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public MidlifeCrisis() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        magicNumber = baseMagicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 99;
        this.tags.add(HumanMod.HumanCardTags.PACIFIST_HUMAN);
    }

    public boolean shouldGlowSpecial() {
        return !HumanUtils.checkHandForAttacks(AbstractDungeon.player);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int apply = HumanUtils.checkHandForAttacks(p) ? magicNumber :
                    defaultSecondMagicNumber;
        this.addToBot(
            new ApplyPowerAction(m, m, new WeakPower(m, apply, false)));
        this.addToBot(
            new ApplyPowerAction(m, m, new FrailPower(m, apply, false)));
        this.addToBot(
            new ApplyPowerAction(m, m, new VulnerablePower(m, apply, false)));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = shouldGlowSpecial() ? Color.FOREST.cpy() :
                         AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
