package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.patches.subscribers.OnManualDiscardSubscriber;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class StoicismPower extends AbstractPower
    implements CloneablePowerInterface, OnManualDiscardSubscriber {

    public static final String POWER_ID = HumanMod.makeID("StoicismPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("StoicismPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("StoicismPower_32.png"));
    private final boolean upgraded;
    public AbstractCreature source;

    public StoicismPower(final AbstractCreature owner,
                         final AbstractCreature source, final int amount,
                         final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.upgraded = upgraded;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onManualDiscard() {
        this.flash();
        this.addToBot(new ApplyPowerAction(owner, owner,
                                           new PeacefulPower(owner, owner,
                                                             amount)));
        if (upgraded) {
            this.addToBot(new ApplyPowerAction(owner, owner,
                                               new HealthinessPower(owner,
                                                                    owner,
                                                                    amount)));
        }
    }

    @Override
    public void updateDescription() {
        if (!upgraded) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new StoicismPower(owner, source, amount, upgraded);
    }
}
