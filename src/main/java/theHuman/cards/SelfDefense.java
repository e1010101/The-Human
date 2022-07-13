package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.tokens.PepperSpray;
import theHuman.cards.tokens.Revolver;
import theHuman.characters.TheHuman;

import java.util.ArrayList;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class SelfDefense extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(SelfDefense.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("SelfDefense.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public SelfDefense() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        List<AbstractCard> ls = new ArrayList<>();
        ls.add(new Revolver());
        ls.add(new PepperSpray());
        ls.add(new KungFu());
        ls.add(new Defend());
        cardsToPreviewList = ls;
        exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.selfRetain = true;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(new Revolver(), 1));
        this.addToBot(new MakeTempCardInHandAction(new PepperSpray(), 1));
        this.addToBot(new MakeTempCardInHandAction(new KungFu(), 1));
        this.addToBot(new MakeTempCardInHandAction(new Defend(), 1));
    }
}
