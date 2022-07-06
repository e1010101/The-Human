package theHuman.cards.guns;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;
import theHuman.powers.TargetedPower;

import java.util.ArrayList;

import static theHuman.HumanMod.makeCardPath;

public class AWP extends AbstractShootWeaponCard {

	public static final String ID = HumanMod.makeID(AWP.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("AWP.png");
	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 2;
	private static final int DAMAGE = 40;
	private static final int UPGRADE_PLUS_DMG = 4;
	private static final int UPGRADE_PLUS_SECOND_MAGIC = 3;

	public AWP() {
		this(0);
	}

	public AWP(int upgrades) {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, new ArrayList<>());
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 1;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 68;
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
		CardCrawlGame.sound.playAV("AWP", 1.0F, 3.5F);
		this.addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), 0.0F));
		this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber, magicNumber));
		this.addToBot(new ApplyPowerAction(m, m, new TargetedPower(m, m, magicNumber)));
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
		return new AWP();
	}
}
