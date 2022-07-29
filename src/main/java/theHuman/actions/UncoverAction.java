package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theHuman.patches.UncoveredField;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class UncoverAction extends AbstractGameAction {

    private boolean cardsAdded = false;

    public UncoverAction(int cardsToAdd) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = cardsToAdd;
    }

    public void update() {
        if (!this.cardsAdded) {
            while (amount > 0) {
                ArrayList<AbstractCard> library = CardLibrary.getAllCards();
                library.removeIf(c -> (c.color != AbstractCard.CardColor.RED &&
                                       c.color !=
                                       AbstractCard.CardColor.GREEN &&
                                       c.color != AbstractCard.CardColor.BLUE &&
                                       c.color !=
                                       AbstractCard.CardColor.PURPLE &&
                                       c.color !=
                                       AbstractCard.CardColor.COLORLESS));
                library.removeIf(c -> c.type == AbstractCard.CardType.CURSE ||
                                      c.type == AbstractCard.CardType.STATUS ||
                                      c.rarity ==
                                      AbstractCard.CardRarity.SPECIAL);
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
                disCard.setCostForTurn(0);
                disCard.purgeOnUse = true;
                UncoveredField.uncovered.set(disCard, true);
                amount--;
            }
            this.cardsAdded = true;
        }
        this.tickDuration();
    }
}
