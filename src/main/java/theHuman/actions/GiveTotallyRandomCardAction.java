package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class GiveTotallyRandomCardAction extends AbstractGameAction {

    private boolean cardsAdded = false;

    public GiveTotallyRandomCardAction(int cardsToAdd) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = cardsToAdd;
    }

    public void update() {
        if (!this.cardsAdded) {
            while (amount > 0) {
                ArrayList<AbstractCard> library = CardLibrary.getAllCards();
                AbstractCard disCard =
                    library.get(cardRandomRng.random(library.size() - 1))
                           .makeCopy();

                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(
                        new ShowCardAndAddToHandEffect(disCard,
                                                       (float) Settings.WIDTH /
                                                       2.0F,
                                                       (float) Settings.HEIGHT /
                                                       2.0F));
                } else {
                    AbstractDungeon.effectList.add(
                        new ShowCardAndAddToDiscardEffect(disCard,
                                                          (float) Settings.WIDTH /
                                                          2.0F,
                                                          (float) Settings.HEIGHT /
                                                          2.0F));
                }
                amount--;
            }
            this.cardsAdded = true;
        }
        this.tickDuration();
    }
}
