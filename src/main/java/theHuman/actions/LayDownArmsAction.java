package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Iterator;

public class LayDownArmsAction extends AbstractGameAction {

    private final AbstractCard layDownArms;

    private boolean finished = false;

    public LayDownArmsAction(AbstractCard layDownArmsCard) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.layDownArms = layDownArmsCard;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (!finished) {
                Iterator<AbstractCard> handCards =
                    AbstractDungeon.player.hand.group.iterator();
                Iterator<AbstractCard> drawCards =
                    AbstractDungeon.player.drawPile.group.iterator();
                Iterator<AbstractCard> discardCards =
                    AbstractDungeon.player.discardPile.group.iterator();
                Iterator<AbstractCard> exhaustCards =
                    AbstractDungeon.player.exhaustPile.group.iterator();
                while (handCards.hasNext()) {
                    AbstractCard c = handCards.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        handCards.remove();
                        removeFromDeck(c);
                        AbstractDungeon.player.hand.removeCard(c);
                    }
                }
                while (drawCards.hasNext()) {
                    AbstractCard c = drawCards.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        drawCards.remove();
                        removeFromDeck(c);
                        AbstractDungeon.player.drawPile.removeCard(c);
                    }
                }
                while (discardCards.hasNext()) {
                    AbstractCard c = discardCards.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        discardCards.remove();
                        removeFromDeck(c);
                        AbstractDungeon.player.discardPile.removeCard(c);
                    }
                }
                while (exhaustCards.hasNext()) {
                    AbstractCard c = exhaustCards.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        exhaustCards.remove();
                        removeFromDeck(c);
                        AbstractDungeon.player.exhaustPile.removeCard(c);
                    }
                }
                removeFromDeck(this.layDownArms);
                finished = true;
            }
        }
        this.tickDuration();
    }

    private void removeFromDeck(AbstractCard c) {
        AbstractDungeon.topLevelEffects.add(
            new PurgeCardEffect(c, (float) Settings.WIDTH / 2,
                                (float) Settings.HEIGHT / 2));
        this.addToTop(new RemoveCardFromMasterDeckAction(c));
    }
}
