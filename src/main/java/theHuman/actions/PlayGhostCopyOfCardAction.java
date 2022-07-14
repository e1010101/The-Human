package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayGhostCopyOfCardAction extends AbstractGameAction {

    private final AbstractCard card;
    private final int times;

    public PlayGhostCopyOfCardAction(AbstractCard c, AbstractMonster target,
                                     int times) {
        this.card = c;
        this.target = target == null ?
                      AbstractDungeon.getCurrRoom().monsters.getRandomMonster(
                          true) : target;
        this.times = times;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public PlayGhostCopyOfCardAction(AbstractCard c) {
        this(c, null, 1);
    }

    @Override
    public void update() {
        if (!this.isDone) {
            AbstractCard tmp = this.card.makeStatEquivalentCopy();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = this.card.current_x;
            tmp.current_y = this.card.current_y;
            tmp.target_x =
                (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;

            if (this.target != null) {
                tmp.calculateCardDamage((AbstractMonster) this.target);
            }

            tmp.applyPowers();
            tmp.purgeOnUse = true;
            tmp.dontTriggerOnUseCard = true;
            for (int i = 0; i < this.times; i++) {
                AbstractDungeon.actionManager.addCardQueueItem(
                    new CardQueueItem(tmp, (AbstractMonster) this.target, 0,
                                      true, true), true);
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}
