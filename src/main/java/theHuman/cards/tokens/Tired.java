package theHuman.cards.tokens;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;

import static theHuman.HumanMod.makeCardPath;

public class Tired extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Tired.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Tired.png");

	private static final CardRarity RARITY = CardRarity.CURSE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.CURSE;
	private static final int COST = -2;

	public Tired() {
		super(ID, IMG, COST, TYPE, CardColor.CURSE, RARITY, TARGET);
		isEthereal = true;
	}

	@Override
	public void upgrade() {
		initializeDescription();
	}

	public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
	}

	public void triggerWhenDrawn() {
		this.addToBot(new ExhaustAction(1, true, false));
	}
}
