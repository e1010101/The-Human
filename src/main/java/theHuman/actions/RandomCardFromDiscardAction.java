package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RandomCardFromDiscardAction extends AbstractGameAction {

    private final int amount;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean done = false;

    public RandomCardFromDiscardAction(int numberOfCards) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = numberOfCards;
    }

    @Override
    public void update() {
        if (!done) {
            CardGroup pile = p.discardPile;
            CardGroup hand = p.hand;
            for (int i = 0; i < this.amount; i++) {
                if (10 == hand.size()) {
                    this.p.createHandIsFullDialog();
                    break;
                }
                if (!pile.isEmpty()) {
                    AbstractCard c = getRandomSalvage(pile);
                    hand.addToHand(c);
                    pile.removeCard(c);
                    c.lighten(false);
                    c.unhover();
                    c.applyPowers();
                    this.p.hand.refreshHandLayout();
                }
            }
            done = true;
            this.tickDuration();
        }
    }

    private AbstractCard getRandomSalvage(CardGroup pile) {
        return pile.getRandomCard(true);
    }
}
