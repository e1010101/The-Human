package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.TechniquePower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class Uppercut extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Uppercut.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Uppercut.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 1;

	private static final int DAMAGE = 6;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final TooltipInfo toolTipInfo = new TooltipInfo("[#a83277]Technique", "Whenever you use a Technique, if the last card you played was also a Technique, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Collections.singletonList(toolTipInfo);

	public Uppercut() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		damage = baseDamage = DAMAGE;
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
			this.shuffleBackIntoDrawPile = true;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

		if (p.hasPower(TechniquePower.POWER_ID)) {
			this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
		} else {
			this.addToBot(new ApplyPowerAction(p, p, new TechniquePower(p, p, 1)));
		}
	}

	@Override
	public void triggerWhenDrawn() {
		this.addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}

	@Override
	public void triggerOnGlowCheck() {
		this.glowColor = shouldGlowSpecial() ? Color.valueOf("a83277") : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
	}
}
