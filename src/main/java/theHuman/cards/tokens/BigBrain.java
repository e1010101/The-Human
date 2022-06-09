package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.SingleCardCostsXAction;
import theHuman.cards.AbstractDynamicCard;
import theHuman.characters.TheHuman;

import java.util.ArrayList;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class BigBrain extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(BigBrain.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("BigBrain.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 0;

	public BigBrain() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		ArrayList<AbstractCard> cardsList = new ArrayList<>();
		for (AbstractCard c : p.hand.group) {
			if (c != this && (c.type != CardType.STATUS && c.type != CardType.CURSE)) {
				cardsList.add(c);
			}
		}
		AbstractCard chosen = cardsList.get(AbstractDungeon.cardRandomRng.random(cardsList.size() - 1));
		this.addToBot(new SingleCardCostsXAction(chosen, 0, false));
	}
}
