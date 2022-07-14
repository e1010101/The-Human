package theHuman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SuppressWarnings("unused")
public class SleepPatch {
    public SleepPatch() {
    }

    @SpirePatch(clz = AbstractMonster.class, method = "rollMove")
    public static class RollMove {
        public RollMove() {
        }

        public static SpireReturn<Void> Prefix(AbstractMonster __instance) {
            return __instance.hasPower("theHuman:SleepPower") ?
                   SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GetNextAction {
        public GetNextAction() {
        }

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(
                        "com.megacrit.cardcrawl.monsters.AbstractMonster") &&
                        m.getMethodName().equals("takeTurn")) {
                        m.replace(
                            "if (!m.hasPower(theHuman.powers.SleepPower.POWER_ID)) {$_ = $proceed($$);}");
                    }
                }
            };
        }
    }
}
