package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

public class FireGunRandomlyAction extends AbstractGameAction {

    private final int criticalChance;
    private final Random rand = new Random();
    private int damage;
    private int timesToShoot;

    public FireGunRandomlyAction(int dmg, int critchance, int times) {
        this.damage = dmg;
        this.criticalChance = critchance;
        this.timesToShoot = times;
    }

    @Override
    public void update() {
        while (this.timesToShoot > 0) {
            AbstractMonster mo =
                AbstractDungeon.getMonsters().getRandomMonster(null, true);
            int storeDamage = damage;
            damage = calculateDamage(damage, criticalChance);
            this.addToTop(new DamageAction(mo, new DamageInfo(
                AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL)));
            damage = storeDamage;
            this.timesToShoot--;
        }
        this.tickDuration();
    }

    public int calculateDamage(int damage, int critChance) {
        return (rand.random(100) < critChance) ? damage * 2 : damage;
    }
}
