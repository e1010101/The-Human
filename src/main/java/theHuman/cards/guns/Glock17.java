package theHuman.cards.guns;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;

import java.util.ArrayList;

import static theHuman.HumanMod.makeCardPath;

public class Glock17 extends AbstractShootWeaponCard {

	public static final String ID = HumanMod.makeID(Glock17.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Glock17.png");
	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 1;
	private static final int DAMAGE = 10;
	private static final int MAGIC_NUMBER = 1;
	private static final int SECOND_MAGIC_NUMBER = 13;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int UPGRADE_PLUS_SECOND_MAGIC = 2;

	public Glock17() {
		this(0);
	}

	public Glock17(int upgrades) {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, new ArrayList<>());
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = MAGIC_NUMBER;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = SECOND_MAGIC_NUMBER;
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
	public void masteryEffect() {
		this.addToBot(new VFXAction(
			new ShowCardBrieflyEffect(this.makeStatEquivalentCopy())));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Glock17(this.timesUpgraded);
	}
}
