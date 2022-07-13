package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.PeacefulPower;
import theHuman.powers.SilverGenerationPower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class SilverGeneration extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(SilverGeneration.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("SilverGeneration.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":AgeingWords");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final TooltipInfo toolTipInfo =
        new TooltipInfo(uiStrings.TEXT[0], uiStrings.TEXT[1]);
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public SilverGeneration() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            upgradeBaseCost(UPGRADED_COST);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new ApplyPowerAction(p, p, new PeacefulPower(p, p, block)));
        this.addToBot(new ApplyPowerAction(p, p, new SilverGenerationPower(p, p,
                                                                           magicNumber)));
    }
}
