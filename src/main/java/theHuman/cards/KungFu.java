package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.HealthinessPower;
import theHuman.powers.TechniquePower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class KungFu extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(KungFu.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("KungFu.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 1;

	private static final int DAMAGE = 5;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final TooltipInfo toolTipInfo = new TooltipInfo("[#a83277]Technique", "Whenever you use a Technique, if the last card you played was also a Technique, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Collections.singletonList(toolTipInfo);

	public KungFu() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		damage = baseDamage = DAMAGE;
		isMultiDamage = true;
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
			rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		this.addToBot(new ApplyPowerAction(p, p, new HealthinessPower(p, p, 1), 1));

		if (p.hasPower(TechniquePower.POWER_ID)) {
			this.addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		} else {
			this.addToBot(new ApplyPowerAction(p, p, new TechniquePower(p, p, 1)));
		}
	}

	@Override
	public void triggerOnGlowCheck() {
		this.glowColor = shouldGlowSpecial() ? Color.valueOf("a83277") : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
	}
}
