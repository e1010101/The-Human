package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.FreedomOfMindPower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class FreedomOfMind extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(FreedomOfMind.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("FreedomOfMind.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final TooltipInfo toolTipInfo = new TooltipInfo("Fetch",
                                                                   "Choose a card from your draw " +
                                                                   "pile and " +
                                                                   "add it to" +
                                                                   " your hand.");
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public FreedomOfMind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new ApplyPowerAction(p, p, new FreedomOfMindPower(p, p, 1), 1));
    }
}
