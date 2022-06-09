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

public class DesertEagle extends AbstractShootWeaponCard {

	public static final String ID = HumanMod.makeID(DesertEagle.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("DesertEagle.png");
	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 1;
	private static final int DAMAGE = 12;
	private static final int UPGRADE_PLUS_DMG = 4;
	private static final int UPGRADE_PLUS_SECOND_MAGIC = 4;

	public DesertEagle() {
		this(0);
	}

	public DesertEagle(int upgrades) {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 1;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 33;
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
		CardCrawlGame.sound.playAV("desertEagle", 1.0F, 0.6F);
		this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber, magicNumber));
		this.addToBot(new MakeTempCardInDrawPileAction(new EmptyCartridge(), magicNumber, true, true));
	}

	@Override
	public void triggerWhenDrawn() {
		selfRetain = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new DesertEagle(this.timesUpgraded);
	}
}
