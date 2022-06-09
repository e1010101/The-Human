package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import theHuman.HumanMod;
import theHuman.actions.FindJunkAction;
import theHuman.characters.TheHuman;
import theHuman.powers.HealthinessPower;

import static theHuman.HumanMod.makeCardPath;

public class InThePark extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(InThePark.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("InThePark.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 2;

	public InThePark() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		magicNumber = baseMagicNumber = 2;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			this.selfRetain = true;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new HealthinessPower(p, p, magicNumber)));
		this.addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
		this.addToBot(new FindJunkAction(defaultSecondMagicNumber));
		this.addToBot(new DrawCardAction(1));
	}
}
