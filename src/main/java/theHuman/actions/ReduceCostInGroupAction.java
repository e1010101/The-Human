package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class ReduceCostInGroupAction extends AbstractGameAction {

    private final ArrayList<AbstractCard> group;

    public ReduceCostInGroupAction(ArrayList<AbstractCard> group, int amount) {
        this.group = group;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
    }

    @Override
    public void update() {
        this.group.forEach(c -> c.setCostForTurn(this.amount - c.cost));
        this.isDone = true;
    }
}

