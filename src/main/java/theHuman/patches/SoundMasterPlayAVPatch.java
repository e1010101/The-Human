package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;

@SuppressWarnings("unused")
public class SoundMasterPlayAVPatch {

    public static HashMap<String, Sfx> map = new HashMap<>();

    static {
        map.put("AK47", load("AK47.mp3"));
        map.put("desertEagle", load("desertEagle.mp3"));
        map.put("AWP", load("AWP.mp3"));
    }

    private static Sfx load(String filename) {
        return new Sfx("theHumanResources/audio/sound/" + filename, false);
    }

    @SpirePatch(clz = SoundMaster.class, method = "playAV", paramtypez = {
        String.class, float.class, float.class})
    public static class PlayAV {

        @SpirePostfixPatch
        public static long Postfix(long res, SoundMaster __instance, String key,
                                   float pitchAdjust, float volumeMod) {
            if (map.containsKey(key)) {

                return map.get(key).play(
                    Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * volumeMod,
                    1.0F + pitchAdjust, 0.0F);
            }
            return 0L;
        }
    }
}
