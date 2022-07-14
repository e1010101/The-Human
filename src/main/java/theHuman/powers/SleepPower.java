package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import java.lang.reflect.Field;

import static theHuman.HumanMod.makePowerPath;

public class SleepPower extends AbstractPower
    implements CloneablePowerInterface {

    public static final String POWER_ID = HumanMod.makeID("SleepPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("SleepPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("SleepPower_32.png"));
    public AbstractMonster monster;
    private byte moveByte;
    private Intent moveIntent;
    private EnemyMoveInfo move;

    public SleepPower(final AbstractMonster owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.monster = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.amount <= 0) {
            this.addToBot(
                new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToBot(
                new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            this.addToBot(
                new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return damageAmount;
    }

    @Override
    public void onRemove() {
        if (this.owner instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster) this.owner;
            if (this.move != null) {
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage,
                          this.move.multiplier, this.move.isMultiDamage);
            } else {
                m.setMove(this.moveByte, this.moveIntent);
            }

            m.createIntent();
            m.applyPowers();
        }
    }

    @Override
    public void onInitialApplication() {
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (SleepPower.this.owner instanceof AbstractMonster) {
                    SleepPower.this.moveByte =
                        ((AbstractMonster) SleepPower.this.owner).nextMove;
                    SleepPower.this.moveIntent =
                        ((AbstractMonster) SleepPower.this.owner).intent;

                    try {
                        Field f =
                            AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        SleepPower.this.move =
                            (EnemyMoveInfo) f.get(SleepPower.this.owner);
                        SleepPower.this.move.intent = Intent.SLEEP;
                        ((AbstractMonster) SleepPower.this.owner).createIntent();
                    } catch (NoSuchFieldException |
                             IllegalAccessException var2) {
                        var2.printStackTrace();
                    }
                }

                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractPower makeCopy() {
        return new SleepPower(monster, amount);
    }
}
