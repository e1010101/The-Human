package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.HumanMod;

import java.util.Iterator;

public class TransformJunkAction extends AbstractGameAction {

    public TransformJunkAction() {
        this.duration = (this.startDuration = Settings.ACTION_DUR_MED);
    }

    public void update() {
        int count = 0;
        int count2;
        if (duration == Settings.ACTION_DUR_MED) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.tags.contains(HumanMod.HumanCardTags.JUNK_HUMAN)) {
                    count++;
                }
            }
            count2 = count;

            Iterator<AbstractCard> cards =
                AbstractDungeon.player.hand.group.iterator();
            while (count > 0) {
                AbstractCard c = cards.next();
                if (c.tags.contains(HumanMod.HumanCardTags.JUNK_HUMAN)) {
                    cards.remove();
                    AbstractDungeon.player.hand.moveToExhaustPile(c);
                    c.triggerOnExhaust();
                    count--;
                }
            }

            this.addToTop(new UncoverAction(count2));
        }
        this.tickDuration();
    }
}
