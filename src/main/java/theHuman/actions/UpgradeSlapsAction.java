package theHuman.actions;

import basemod.helpers.CardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.HumanMod;

public class UpgradeSlapsAction extends AbstractGameAction {

    private final boolean isMultiplier;

    public UpgradeSlapsAction(int amount, boolean isMultiplier) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
        this.isMultiplier = isMultiplier;
    }

    @Override
    public void update() {
        for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
            BoostCard(c);
        }
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (BoostCard(c)) {
                c.flash();
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
            BoostCard(c);
        }
        this.isDone = true;
    }

    public boolean BoostCard(AbstractCard c) {
        boolean wasBoosted = false;
        if (CardTags.hasTag(c, HumanMod.HumanCardTags.SLAP_HUMAN)) {
            wasBoosted = true;
            if (this.isMultiplier) {
                c.baseDamage *= this.amount;
            } else {
                c.baseDamage += this.amount;
            }
        }
        return wasBoosted;
    }
}
