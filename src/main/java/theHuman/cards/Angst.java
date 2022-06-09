package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.FollowUpAction;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class Angst extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Angst.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Angst.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 0;

	public Angst() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.selfRetain = true;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DrawCardAction(3));
		this.addToBot(new FollowUpAction(() -> {
			if (AbstractDungeon.player.hand.size() > 5) {
				this.addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 10, true));
			}
		}, 1.5F));
	}
}
