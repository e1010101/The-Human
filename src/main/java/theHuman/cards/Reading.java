package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.tokens.BigBrain;
import theHuman.cards.tokens.DeepThought;
import theHuman.characters.TheHuman;
import theHuman.powers.HealthinessPower;
import theHuman.powers.ReadingPower;

import static theHuman.HumanMod.makeCardPath;

public class Reading extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Reading.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("Reading.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	private static final int COST = 2;

	public Reading() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		cardsToPreview = new DeepThought();
		magicNumber = baseMagicNumber = 3;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(2);
			this.isInnate = true;
			rawDescription = UPGRADE_DESCRIPTION;
			cardsToPreview = new BigBrain();
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new HealthinessPower(p, p, magicNumber)));
		this.addToBot(new ApplyPowerAction(p, p, new ReadingPower(p, p, 1, upgraded)));
	}
}
