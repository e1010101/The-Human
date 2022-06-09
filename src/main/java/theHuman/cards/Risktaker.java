package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.RisktakerPower;

import static theHuman.HumanMod.makeCardPath;

public class Risktaker extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Risktaker.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Risktaker.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;

	public Risktaker() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		magicNumber = baseMagicNumber = 1;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.isInnate = true;
			upgradeBaseCost(UPGRADED_COST);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new RisktakerPower(p, p, 1)));
	}
}
