package theHuman.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.RandomCardFromDiscardAction;
import theHuman.actions.ShuffleHandAction;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class Salvage extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Salvage.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Salvage.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public Salvage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int size = p.hand.size();
        this.addToBot(new ShuffleHandAction());
        for (int i = 0; i < size; i++) {
            this.addToBot(new RandomCardFromDiscardAction(1));
        }
    }
}
