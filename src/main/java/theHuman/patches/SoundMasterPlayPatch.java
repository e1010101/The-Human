package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;

@SuppressWarnings("unused")
public class SoundMasterPlayPatch {

    public static HashMap<String, Sfx> map = new HashMap<>();

    static {
        map.put("AK47", load("AK47.mp3"));
        map.put("desertEagle", load("desertEagle.mp3"));
        map.put("AWP", load("AWP.mp3"));
    }

    private static Sfx load(final String filename) {
        return new Sfx("theHumanResources/audio/sound/" + filename, false);
    }

    @SpirePatch(clz = SoundMaster.class, method = "play", paramtypez = {
        String.class, boolean.class})
    public static class Play {

        @SpirePostfixPatch
        public static long Postfix(final long res, final SoundMaster __instance,
                                   final String key,
                                   final boolean useBgmVolume) {
            if (map.containsKey(key)) {

                return map.get(key)
                          .play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
            }
            return 0L;
        }
    }
}
