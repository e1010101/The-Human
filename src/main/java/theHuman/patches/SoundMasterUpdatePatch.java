package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundInfo;
import com.megacrit.cardcrawl.audio.SoundMaster;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SuppressWarnings("unused")
public class SoundMasterUpdatePatch {

    @SpirePatch(clz = SoundMaster.class, method = "update")
    public static class Update {

        @SpireInsertPatch(locator = Locator.class, localvars = {"e", "sfx"})
        public static void Insert(final SoundMaster __instance,
                                  final SoundInfo e, @ByRef Sfx[] sfx) {
            if (sfx[0] == null) {
                sfx[0] = theHuman.patches.SoundMasterPlayPatch.map.get(e.name);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws
                                                            CannotCompileException,
                                                            PatchingException {

                Matcher finalMatcher =
                    new Matcher.FieldAccessMatcher(SoundInfo.class, "isDone");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}