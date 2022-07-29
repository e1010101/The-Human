package theHuman.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.ReduceCostInGroupAction;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class ManOfTomorrow extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(ManOfTomorrow.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("ManOfTomorrow.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 5;
    private static final int SECOND_MAGIC_NUMBER = 1;
    private static final int UPGRADED_COST = 4;

    public ManOfTomorrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.selfRetain = true;
        magicNumber = baseMagicNumber = 0;
        defaultSecondMagicNumber =
        defaultBaseSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new FetchAction(p.drawPile, defaultSecondMagicNumber));
        this.addToBot(new ReduceCostInGroupAction(p.hand.group, magicNumber));
        this.addToBot(
            new ReduceCostInGroupAction(p.discardPile.group, magicNumber));
        this.addToBot(
            new ReduceCostInGroupAction(p.drawPile.group, magicNumber));
    }
}
