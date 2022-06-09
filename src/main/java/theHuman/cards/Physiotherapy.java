package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.HealthinessPower;

import static theHuman.HumanMod.makeCardPath;

public class Physiotherapy extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Physiotherapy.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Physiotherapy.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	private static final int BLOCK = 40;
	private static final int UPGRADE_PLUS_BLOCK = 15;

	public Physiotherapy() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		block = baseBlock = BLOCK;
		magicNumber = baseMagicNumber = 4;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ExhaustAction(5, false, false, false));
		this.addToBot(new ApplyPowerAction(p, p, new HealthinessPower(p, p, magicNumber)));
		this.addToBot(new GainBlockAction(p, p, block));
	}
}
