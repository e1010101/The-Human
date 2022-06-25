package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.FindJunkAction;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class DumpsterDiving extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(DumpsterDiving.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("DumpsterDiving.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 1;

	public DumpsterDiving() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		magicNumber = baseMagicNumber = 2;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDefaultSecondMagicNumber(1);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new BetterDiscardPileToHandAction(magicNumber, true));
		this.addToBot(new FindJunkAction(defaultSecondMagicNumber));
	}
}
