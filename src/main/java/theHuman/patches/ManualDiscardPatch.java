package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.patches.subscribers.OnManualDiscardSubscriber;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ManualDiscardPatch {

    @SpirePatch(clz = AbstractCard.class, method = "triggerOnManualDiscard")
    public static class onManualDiscard {

        @SpirePostfixPatch
        public static void postfix() {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractPower power : p.powers) {
                if (power instanceof OnManualDiscardSubscriber) {
                    ((OnManualDiscardSubscriber) power).onManualDiscard();
                }
            }

            for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile,
                                                     p.discardPile)) {
                for (AbstractCard card : cardGroup.group) {
                    if (card instanceof OnManualDiscardSubscriber) {
                        ((OnManualDiscardSubscriber) card).onManualDiscard();
                    }
                }
            }
        }
    }
}
