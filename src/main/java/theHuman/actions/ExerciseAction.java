package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExerciseAction extends AbstractGameAction {

    private final int multiplier;

    public ExerciseAction(int mult) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = (this.startDuration = Settings.ACTION_DUR_FAST);
        this.multiplier = mult;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractDungeon.handCardSelectScreen.open("Exhaust", 99, true,
                                                      true);
            this.addToTop(new WaitAction(0.25F));
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                int cnt =
                    AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
                this.addToTop(new GainEnergyAction(cnt * multiplier));
            }

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.player.hand.moveToExhaustPile(c);
                c.triggerOnExhaust();
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}
