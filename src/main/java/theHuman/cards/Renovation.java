package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class Renovation extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Renovation.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Renovation.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int SECOND_MAGIC_NUMBER = 5;

    public Renovation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
        defaultSecondMagicNumber =
        defaultBaseSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            this.selfRetain = true;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int size = p.hand.size();
        this.addToBot(new DiscardAction(p, p, size, true));
        this.addToBot(new DrawCardAction(magicNumber));
        size = p.hand.size();
        this.addToBot(new DiscardAction(p, p, size, true));
        this.addToBot(new DrawCardAction(defaultSecondMagicNumber));
    }
}
