package theHuman.actions;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import theHuman.powers.InjuredPower;

import java.util.Set;

public class FireGunRandomlyActionWithEffect extends AbstractGameAction {

    private final int criticalChance;
    private final Random rand = new Random();
    private int damage;
    private int timesToShoot;
    private final float chanceToApply;

    public FireGunRandomlyActionWithEffect(int dmg, int critchance, int times, float chance) {
        this.damage = dmg;
        this.criticalChance = critchance;
        this.timesToShoot = times;
        this.chanceToApply = chance;
    }

    @Override
    public void update() {
        while (this.timesToShoot > 0) {
            AbstractMonster mo = AbstractDungeon.getMonsters()
                                                .getRandomMonster(null, true,
                                                                  AbstractDungeon.cardRandomRng);
            int storeDamage = damage;
            damage = calculateDamage(damage, criticalChance);
            this.addToTop(new DamageAction(mo, new DamageInfo(
                AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL)));
            damage = storeDamage;
            if (MathUtils.random(0, 100) <= chanceToApply * 100) {
                this.addToTop(
                    new ApplyPowerAction(mo, mo, new InjuredPower(mo, mo, 1)));
            }
            this.timesToShoot--;
        }
        this.tickDuration();
    }

    public int calculateDamage(int damage, int critChance) {
        return (rand.random(100) < critChance) ? damage * 2 : damage;
    }
}
