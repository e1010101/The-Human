package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class SpringCleaningAction extends AbstractGameAction {

    private final AbstractPlayer player;
    private final int damage;

    public SpringCleaningAction(int dmg) {
        this.player = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = (this.startDuration = Settings.ACTION_DUR_FAST);
        this.damage = dmg;
        this.damageType = DamageInfo.DamageType.NORMAL;
    }

    public void update() {
        int count = AbstractDungeon.player.discardPile.size();
        Iterator<AbstractCard> discardCards =
            AbstractDungeon.player.discardPile.group.iterator();
        while (discardCards.hasNext()) {
            AbstractCard c = discardCards.next();
            discardCards.remove();
            AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            c.triggerOnExhaust();
        }
        for (int i = 0; i < count; i++) {
            this.addToTop(new DamageAllEnemiesAction(player, damage, damageType,
                                                     AttackEffect.SLASH_HEAVY));
        }
        this.tickDuration();
    }
}
