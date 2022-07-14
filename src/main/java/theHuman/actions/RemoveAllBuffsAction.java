package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class RemoveAllBuffsAction extends AbstractGameAction {

    private final AbstractCreature c;

    public RemoveAllBuffsAction(AbstractCreature c) {
        this.c = c;
        this.duration = 0.5F;
    }

    public void update() {
        Iterator<AbstractPower> var1 = this.c.powers.iterator();

        while (true) {
            AbstractPower p;
            do {
                if (!var1.hasNext()) {
                    this.isDone = true;
                    return;
                }

                p = var1.next();
            } while (p.type != AbstractPower.PowerType.BUFF);

            this.addToTop(new RemoveSpecificPowerAction(this.c, this.c, p.ID));
        }
    }
}
