package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SingleShuffleAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractCard card;
    private boolean shuffleDone = false;

    public SingleShuffleAction(AbstractCard c) {
        this.p = AbstractDungeon.player;
        this.card = c;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (!this.shuffleDone) {
                this.p.hand.moveToDeck(card, true);
                AbstractDungeon.player.hand.refreshHandLayout();
                this.shuffleDone = true;
            }
        }
        this.tickDuration();
    }
}
