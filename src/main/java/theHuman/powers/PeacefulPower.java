package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class PeacefulPower extends AbstractPower
    implements CloneablePowerInterface {
    public static final String POWER_ID = HumanMod.makeID("PeacefulPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("PeacefulPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("PeacefulPower_32.png"));
    public AbstractCreature source;

    public PeacefulPower(final AbstractCreature owner,
                         final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        this.flash();
        int actual_damage = damageAmount - this.amount;
        if (actual_damage < 0) {
            this.amount -= damageAmount;
            updateDescription();
            actual_damage = 0;
        } else {
            AbstractDungeon.actionManager.addToTop(
                new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return actual_damage;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PeacefulPower(owner, source, amount);
    }
}
