package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class ReduceCostInHandAction extends AbstractGameAction {

	private final ArrayList<AbstractCard> cardsInHand;

	public ReduceCostInHandAction(ArrayList<AbstractCard> cardsInHand, int amount) {
		this.cardsInHand = cardsInHand;
		this.duration = Settings.ACTION_DUR_XFAST;
		this.amount = amount;
	}

	@Override
	public void update() {
		this.cardsInHand.forEach(c -> c.setCostForTurn(c.cost - this.amount));
		this.isDone = true;
	}
}

