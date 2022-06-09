package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class Berreta92 extends AbstractShootWeaponCard {

	public static final String ID = HumanMod.makeID(Berreta92.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("Berreta92.png");
	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 1;
	private static final int DAMAGE = 11;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int UPGRADE_PLUS_SECOND_MAGIC = 2;

	public Berreta92() {
		this(0);
	}

	public Berreta92(int upgrades) {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 1;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 10;
		timesUpgraded = upgrades;
	}

	@Override
	public void upgrade() {
		upgradeDamage(UPGRADE_PLUS_DMG);
		if (defaultSecondMagicNumber + UPGRADE_PLUS_SECOND_MAGIC > 100) {
			defaultSecondMagicNumber = 100;
		} else {
			upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC);
		}
		++timesUpgraded;
		upgraded = true;
		name = cardStrings.NAME + "+" + timesUpgraded;
		initializeTitle();
		initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		selfRetain = false;
		this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber, magicNumber));
		this.addToBot(new MakeTempCardInDrawPileAction(new EmptyCartridge(), magicNumber, true, true));
	}

	@Override
	public void triggerWhenDrawn() {
		selfRetain = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Berreta92(this.timesUpgraded);
	}
}
