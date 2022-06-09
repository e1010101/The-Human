package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.patches.subscribers.OnShuffleSubscriber;
import theHuman.powers.CarPower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.makeCardPath;

public class Car extends AbstractDynamicCard implements OnShuffleSubscriber {

	public static final String ID = HumanMod.makeID(Car.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Car.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;

	private static final int BLOCK = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	private static final TooltipInfo toolTipInfo = new TooltipInfo("[#2000a7]Lifestyle", "Whenever you shuffle your draw pile, perform an additional effect.");
	private static final List<TooltipInfo> customTooltips = Collections.singletonList(toolTipInfo);

	public Car() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
		magicNumber = baseMagicNumber = 1;
		baseBlock = BLOCK;
	}

	@Override
	public void onShuffle() {
		AbstractPlayer p = AbstractDungeon.player;
		this.addToBot(new GainBlockAction(p, p, 8));
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			upgradeBaseCost(UPGRADED_COST);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new CarPower(p, p, magicNumber)));
		this.addToBot(new GainBlockAction(p, p, baseBlock));
	}
}
