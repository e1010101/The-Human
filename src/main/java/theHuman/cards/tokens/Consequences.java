package theHuman.cards.tokens;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;

import static theHuman.HumanMod.makeCardPath;

public class Consequences extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Consequences.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("Consequences.png");

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;

    private static final int COST = -2;

    public Consequences() {
        super(ID, IMG, COST, TYPE, CardColor.CURSE, RARITY, TARGET);
        isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToBot(
            new MakeTempCardInDrawPileAction(new Regret(), 1, true, true));
        this.addToBot(
            new MakeTempCardInDrawPileAction(new Shame(), 1, true, true));
    }
}
