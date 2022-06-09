package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.GiveTemporaryCopyOfCardAction;
import theHuman.characters.TheHuman;
import theHuman.powers.TechniquePower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class SuckerPunch extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(SuckerPunch.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("SuckerPunch.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 2;

	private static final int DAMAGE = 10;
	private static final int UPGRADE_PLUS_DMG = 5;
	private static final TooltipInfo toolTipInfo = new TooltipInfo("[#a83277]Technique", "Whenever you use a Technique, if the last card you played was also a Technique, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Collections.singletonList(toolTipInfo);

	public SuckerPunch() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 8;
		cardsToPreview = new Slap();
		this.tags.add(HumanMod.HumanCardTags.TECHNIQUE_HUMAN);
	}

	public boolean shouldGlowSpecial() {
		return AbstractDungeon.player.hasPower(TechniquePower.POWER_ID);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeMagicNumber(3);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new GiveTemporaryCopyOfCardAction(new Slap(), 2));

		if (p.hasPower(TechniquePower.POWER_ID)) {
			this.addToBot(new GainBlockAction(p, magicNumber));
		} else {
			this.addToBot(new ApplyPowerAction(p, p, new TechniquePower(p, p, 1)));
		}
	}

	@Override
	public void triggerWhenDrawn() {
		this.addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}

	@Override
	public void triggerOnGlowCheck() {
		this.glowColor = shouldGlowSpecial() ? Color.valueOf("a83277") : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
	}
}
