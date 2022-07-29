package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.tokens.PocketPistol;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class ConcealedCarry extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(ConcealedCarry.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("ConcealedCarry.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public ConcealedCarry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        cardsToPreview = new PocketPistol();
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
        this.addToBot(new MakeTempCardInHandAction(new PocketPistol()));
    }
}
