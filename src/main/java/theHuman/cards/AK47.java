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

public class AK47 extends AbstractShootWeaponCard {

	public static final String ID = HumanMod.makeID(AK47.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("AK47.png");
	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 2;
	private static final int DAMAGE = 6;
	private static final int UPGRADE_PLUS_DMG = 1;
	private static final int UPGRADE_PLUS_SECOND_MAGIC = 1;

	public AK47() {
		this(0);
	}

	public AK47(int upgrades) {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 4;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 11;
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
		CardCrawlGame.sound.playAV("AK47", 1.0F, 0.85F);
		this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber, magicNumber));
		this.addToBot(new MakeTempCardInDrawPileAction(new EmptyCartridge(), magicNumber, true, true));
	}

	@Override
	public void triggerWhenDrawn() {
		selfRetain = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new AK47(this.timesUpgraded);
	}
}
