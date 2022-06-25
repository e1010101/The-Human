package theHuman.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.tokens.Poverty;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class LegalAction extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(LegalAction.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("LegalAction.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 2;

	private static final int BLOCK = 6;
	private static final int UPGRADE_PLUS_BLOCK = 4;

	public LegalAction() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		block = baseBlock = BLOCK;
		magicNumber = baseMagicNumber = 1;
		cardsToPreview = new Poverty();
		ExhaustiveVariable.setBaseValue(this, 2);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(m, m, new StunMonsterPower(m, magicNumber)));
		this.addToBot(new GainBlockAction(p, p, block));
		this.addToBot(new MakeTempCardInDrawPileAction(new Poverty(), 1, true, true));
	}
}
