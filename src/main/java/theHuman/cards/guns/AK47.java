package theHuman.cards.guns;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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
import theHuman.cards.tokens.Flashbang;
import theHuman.cards.tokens.PocketPistol;
import theHuman.characters.TheHuman;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
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
	private static final int MASTERY_LEVEL = 13;
	private static final String MASTERED_NAME =
		cardStrings.EXTENDED_DESCRIPTION[0];
	private static final String MASTERED_SMALL =
		getModID() + "Resources/images/cards/bonus/camo.png";
	private static final String MASTERED_LARGE =
		getModID() + "Resources/images/cards/bonus/camo_p.png";
	public static final String MASTERED_IMG =
		makeCardPath("AK47_Mastered" + ".png");
	private static final TooltipInfo toolTipInfo =
		new TooltipInfo("[#c20000]Mastery",
						"[#c20000]Mastery X: Can be upgraded any " +
						"number of times. NL NL Upon reaching X " +
						"upgrades, obtain a new form.");
	private static final List<TooltipInfo> customTooltips =
		Collections.singletonList(toolTipInfo);

	public AK47() {
		this(0);
	}

	public AK47(int upgrades) {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 4;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 11;
		timesUpgraded = upgrades;
	}

	@Override
	public void upgrade() {
		timesUpgraded++;
		upgradeDamage(UPGRADE_PLUS_DMG);
		if (defaultSecondMagicNumber + UPGRADE_PLUS_SECOND_MAGIC > 100) {
			defaultSecondMagicNumber = 100;
		} else {
			upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC);
		}
		upgraded = true;
		if (this.timesUpgraded >= MASTERY_LEVEL) {
			name = MASTERED_NAME;
			rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
			this.textureImg = MASTERED_IMG;
			this.loadCardImage(MASTERED_IMG);
			this.setBackgroundTexture(MASTERED_SMALL, MASTERED_LARGE);
		} else {
			name = cardStrings.NAME + " + " + timesUpgraded;
		}
		initializeTitle();
		initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		selfRetain = false;
		CardCrawlGame.sound.playAV("AK47", 1.0F, 0.85F);
		this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber, magicNumber));
		this.addToBot(new MakeTempCardInDrawPileAction(new EmptyCartridge(), magicNumber, true, true));
		if (timesUpgraded >= MASTERY_LEVEL) {
			masteryEffect();
		}
	}

	@Override
	public void triggerWhenDrawn() {
		selfRetain = true;
	}

	@Override
	public void masteryEffect() {
		this.addToBot(new VFXAction(
			new ShowCardBrieflyEffect(this.makeStatEquivalentCopy())));
		this.addToBot(new MakeTempCardInHandAction(new PocketPistol()));
		this.addToBot(new MakeTempCardInHandAction(new Flashbang()));
	}

	@Override
	public AbstractCard makeCopy() {
		return new AK47();
	}
}
