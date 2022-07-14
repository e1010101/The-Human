package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.Slaptacular;

public class SlaptacularAction extends AbstractGameAction {

    public SlaptacularAction() {
        this.duration = (this.startDuration = Settings.ACTION_DUR_MED);
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                if (c.tags.contains(HumanMod.HumanCardTags.SLAP_HUMAN)) {
                    if (c.cardID.equals(Slaptacular.ID)) {
                        continue;
                    }

                    AbstractMonster targetMonster =
                        AbstractDungeon.getCurrRoom().monsters.getRandomMonster(
                            true);

                    AbstractCard tmp = c.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = tmp.target_x =
                        Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
                    tmp.current_y = tmp.target_y = Settings.HEIGHT / 2.0f;
                    if (targetMonster != null) {
                        tmp.calculateCardDamage(targetMonster);
                    }
                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.cardQueue.add(
                        new CardQueueItem(tmp, targetMonster, 0, true, true));
                }
            }
        }
        this.tickDuration();
    }
}
