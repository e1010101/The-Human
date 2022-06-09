package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHuman.HumanMod;
import theHuman.cards.tokens.Tired;
import theHuman.characters.TheHuman;
import theHuman.patches.subscribers.OnShuffleSubscriber;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class NineToFive extends AbstractDynamicCard implements OnShuffleSubscriber {

	public static final String ID = HumanMod.makeID(NineToFive.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("NineToFive.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 2;

	private static final int DAMAGE = 5;
	private static final int UPGRADE_PLUS_DMG = 2;
	private static final TooltipInfo toolTipInfo = new TooltipInfo("[#2000a7]Lifestyle", "Whenever you shuffle your draw pile, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Collections.singletonList(toolTipInfo);

	public NineToFive() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		damage = baseDamage = DAMAGE;
		cardsToPreview = new Tired();
		isMultiDamage = true;
	}

	@Override
	public void onShuffle() {
		AbstractPlayer p = AbstractDungeon.player;
		this.addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, 1)));
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			this.selfRetain = true;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			this.addToBot(new ApplyPowerAction(mo, mo, new PoisonPower(mo, mo, 5)));
			this.addToBot(new ApplyPowerAction(mo, mo, new WeakPower(mo, 3, false)));
		}
		this.addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		this.addToBot(new MakeTempCardInDrawPileAction(new Tired(), 1, true, true));
	}
}
