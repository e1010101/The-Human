package theHuman.patches;

import com.badlogic.gdx.math.RandomXS128;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theHuman.HumanMod;

import java.util.Arrays;

@SuppressWarnings("unused")
public class RoomUpdatePatch {

    private static final RandomXS128 rand = new RandomXS128();
    private static final boolean soundPlayed = false;
    private static final boolean addCardDone = false;

    @SpirePatch(clz = AbstractRoom.class, method = "update")
    public static class Update {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractRoom __instance) {

            AbstractPlayer p = AbstractDungeon.player;
            for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile,
                                                     p.discardPile)) {
                for (AbstractCard card : cardGroup.group) {
                    if (card.hasTag(HumanMod.HumanCardTags.SHOOTER_HUMAN)) {
                        if (card.costForTurn < 1) {
                            card.costForTurn = 1;
                        }
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws
                                                            CannotCompileException,
                                                            PatchingException {

                Matcher finalMatcher =
                    new Matcher.FieldAccessMatcher(AbstractDungeon.class,
                                                   "isScreenUp");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
