package theHuman.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class FollowUpAction extends AbstractGameAction {

    private final Runnable action;

    public FollowUpAction(Runnable action) {
        this(action, 0.0F);
    }

    public FollowUpAction(Runnable action, float delay) {
        this.action = action;
        this.duration = delay;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0.0F) {
            this.action.run();
            this.isDone = true;
        }
    }
}