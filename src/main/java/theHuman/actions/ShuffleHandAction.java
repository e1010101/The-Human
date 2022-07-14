//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class ShuffleHandAction extends AbstractGameAction {

    private boolean shuffled = false;

    public ShuffleHandAction() {
        this.setValues(null, null, 0);
        this.actionType = ActionType.SHUFFLE;

        Iterator<AbstractRelic> var1 = AbstractDungeon.player.relics.iterator();

        while (var1.hasNext()) {
            AbstractRelic r = var1.next();
            r.onShuffle();
        }
    }

    public void update() {
        if (!this.shuffled) {
            this.shuffled = true;
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new PutOnDeckAction(p, p, 99, true));
            p.drawPile.shuffle();
        }
        this.isDone = true;
    }
}
