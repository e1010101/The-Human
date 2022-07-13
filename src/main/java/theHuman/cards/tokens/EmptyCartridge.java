package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class EmptyCartridge extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(EmptyCartridge.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("EmptyCartridge.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;

    private static final int COST = -2;

    public EmptyCartridge() {
        super(ID, IMG, COST, TYPE, CardColor.CURSE, RARITY, TARGET);
        this.dontTriggerOnUseCard = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerWhenDrawn() {
        this.addToBot(new SetDontTriggerAction(this, false));
    }
}
