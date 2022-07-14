package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.cards.Sanitation;

public class SanitationAction extends AbstractGameAction {

    private boolean actionDone = false;

    public SanitationAction() {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (!actionDone) {
            for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
                if (!(card instanceof Sanitation)) {
                    if (card.cost >= 0) {
                        AbstractMonster m =
                            AbstractDungeon.getCurrRoom().monsters.getRandomMonster(
                                null, true, AbstractDungeon.cardRandomRng);
                        AbstractCard tmp = card.makeSameInstanceOf();
                        AbstractDungeon.player.limbo.addToBottom(tmp);
                        tmp.current_x = card.current_x;
                        tmp.current_y = card.current_y;
                        tmp.target_x = (float) Settings.WIDTH / 2.0F -
                                       300.0F * Settings.scale;
                        tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                        tmp.calculateCardDamage(m);
                        tmp.purgeOnUse = true;
                        AbstractDungeon.actionManager.addCardQueueItem(
                            new CardQueueItem(tmp, m, 0, true, true), true);
                    }
                }
            }
            actionDone = true;
        }
        this.tickDuration();
    }
}
