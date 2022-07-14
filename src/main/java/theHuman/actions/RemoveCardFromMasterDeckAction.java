package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveCardFromMasterDeckAction extends AbstractGameAction {

    private final AbstractCard cardToRemove;

    public RemoveCardFromMasterDeckAction(AbstractCard cardToRemove) {
        this.cardToRemove = cardToRemove;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard upgradeMatch = null;
        AbstractCard match = null;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(this.cardToRemove.cardID) &&
                c.upgraded == this.cardToRemove.upgraded) {
                upgradeMatch = c;
                break;
            }
            if (c.cardID.equals(this.cardToRemove.cardID)) {
                match = c;
            }
        }
        if (null != upgradeMatch) {
            doRemovalFromMasterDeck(upgradeMatch);
        } else if (null != match) {
            doRemovalFromMasterDeck(match);
        }
        this.isDone = true;
    }

    private void doRemovalFromMasterDeck(AbstractCard c) {
        this.cardToRemove.lighten(true);
        this.cardToRemove.stopGlowing();
        this.cardToRemove.unhover();
        this.cardToRemove.unfadeOut();
        AbstractDungeon.player.masterDeck.removeCard(c);
    }
}
