package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.AgeingPower;
import theHuman.util.HumanUtils;

import java.util.Arrays;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class Ageing extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Ageing.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Ageing.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	private static final TooltipInfo toolTipInfo = new TooltipInfo("Ageing", "At the start of your turn, if your have 14 or more stacks of this, die. NL At the end of your turn, gain a stack of this. NL For each stack of this you have, reduce your damage dealt by 2%.");
	private static final TooltipInfo toolTipInfo2 = new TooltipInfo("[#34eb52]Pacifist", "If your hand has no Attacks, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Arrays.asList(toolTipInfo, toolTipInfo2);

	public Ageing() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		isInnate = true;
		exhaust = true;
		this.tags.add(HumanMod.HumanCardTags.PACIFIST_HUMAN);
	}

	public boolean shouldGlowSpecial() {
		return !HumanUtils.checkHandForAttacks(AbstractDungeon.player);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			this.addToBot(new ApplyPowerAction(mo, mo, new AgeingPower(mo, mo, 1)));
		}

		if (!HumanUtils.checkHandForAttacks(p)) {
			this.addToBot(new GainBlockAction(p, 10));
		}
	}

	@Override
	public void triggerOnGlowCheck() {
		this.glowColor = shouldGlowSpecial() ? Color.FOREST.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
	}
}
