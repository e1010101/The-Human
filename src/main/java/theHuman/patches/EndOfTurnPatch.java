package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Arrays;
import java.util.Iterator;

@SuppressWarnings("unused")
public class EndOfTurnPatch {

    @SpirePatch(clz = AbstractRoom.class, method = "endTurn")
    public static class EndTurn {

        @SpirePrefixPatch
        public static void Prefix(AbstractRoom __instance) {

            if (AbstractDungeon.getMonsters() != null) {

                AbstractPlayer p = AbstractDungeon.player;

                for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile,
                                                         p.discardPile)) {
                    Iterator<AbstractCard> iterator =
                        cardGroup.group.iterator();
                    while (iterator.hasNext()) {
                        AbstractCard c = iterator.next();

                        if (UncoveredField.uncovered.get(c)) {
                            iterator.remove();
                            AbstractDungeon.topLevelEffects.add(
                                new PurgeCardEffect(c,
                                                    (float) Settings.WIDTH / 2,
                                                    (float) Settings.HEIGHT /
                                                    2));
                            cardGroup.removeCard(c);
                        }
                    }
                }
            }
        }
    }
}
