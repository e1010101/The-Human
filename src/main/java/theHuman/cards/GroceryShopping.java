package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.DrawCardWithTypeAction;
import theHuman.characters.TheHuman;
import theHuman.patches.subscribers.OnShuffleSubscriber;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class GroceryShopping extends AbstractDynamicCard implements OnShuffleSubscriber {

	public static final String ID = HumanMod.makeID(GroceryShopping.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("GroceryShopping.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 2;

	private static final TooltipInfo toolTipInfo = new TooltipInfo("[#2000a7]Lifestyle", "Whenever you shuffle your draw pile, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Collections.singletonList(toolTipInfo);

	public GroceryShopping() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		magicNumber = baseMagicNumber = 5;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
	}

	@Override
	public void onShuffle() {
		if (!upgraded) {
			this.addToBot(new DrawCardAction(defaultSecondMagicNumber));
		} else {
			this.addToBot(new ScryAction(magicNumber));
		}
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ScryAction(magicNumber));
		this.addToBot(new DrawCardWithTypeAction(CardType.ATTACK, defaultSecondMagicNumber));
		this.addToBot(new DrawCardWithTypeAction(CardType.SKILL, defaultSecondMagicNumber));
		this.addToBot(new DrawCardWithTypeAction(CardType.POWER, defaultSecondMagicNumber));
	}
}
