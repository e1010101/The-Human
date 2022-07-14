package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.patches.subscribers.OnShuffleSubscriber;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ShufflePatch {
    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "<ctor>")
    public static class EmptyDeckShuffleAction0 {
        @SpirePostfixPatch
        public static void Postfix(EmptyDeckShuffleAction _inst) {
            AbstractPlayer p = AbstractDungeon.player;
            for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile,
                                                     p.discardPile)) {
                for (AbstractCard card : cardGroup.group) {
                    if (card instanceof OnShuffleSubscriber) {
                        ((OnShuffleSubscriber) card).onShuffle();
                    }
                }
            }
        }
    }

    @SpirePatch(clz = ShuffleAllAction.class, method = "<ctor>")
    public static class ShuffleAllAction0 {
        @SpirePostfixPatch
        public static void Postfix(ShuffleAllAction _inst) {
            AbstractPlayer p = AbstractDungeon.player;
            for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile,
                                                     p.discardPile)) {
                for (AbstractCard card : cardGroup.group) {
                    if (card instanceof OnShuffleSubscriber) {
                        ((OnShuffleSubscriber) card).onShuffle();
                    }
                }
            }
        }
    }

    @SpirePatch(clz = ShuffleAction.class, method = "update")
    public static class update {
        @SpireInsertPatch(rloc = 0)
        public static void Insert(ShuffleAction _inst) {
            AbstractPlayer p = AbstractDungeon.player;
            for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile,
                                                     p.discardPile)) {
                for (AbstractCard card : cardGroup.group) {
                    if (card instanceof OnShuffleSubscriber) {
                        ((OnShuffleSubscriber) card).onShuffle();
                    }
                }
            }
        }
    }
}
