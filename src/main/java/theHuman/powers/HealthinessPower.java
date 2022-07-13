package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import java.util.Random;

import static theHuman.HumanMod.makePowerPath;

public class HealthinessPower extends AbstractPower
    implements CloneablePowerInterface {
    public static final String POWER_ID = HumanMod.makeID("HealthinessPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("HealthinessPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("HealthinessPower_32.png"));
    private final Random rand = new Random();
    public AbstractCreature source;

    public HealthinessPower(final AbstractCreature owner,
                            final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;
        canGoNegative = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount <= 10) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[5] + DESCRIPTIONS[11];
        } else if (amount <= 18) {
            description = DESCRIPTIONS[1] + DESCRIPTIONS[4] + DESCRIPTIONS[10];
        } else {
            description = DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[6] +
                          amount / 3 + DESCRIPTIONS[7] + amount / 3 +
                          DESCRIPTIONS[8] + DESCRIPTIONS[9];
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        if (amount > 18) {
            int randEffect = rand.nextInt(4);
            switch (randEffect) {
                case 0:
                    this.addToBot(new DrawCardAction(1));
                    break;
                case 1:
                    this.addToBot(new GainEnergyAction(1));
                    break;
                case 2:
                    this.addToBot(new GainBlockAction(owner, 5));
                    break;
                case 3:
                    this.addToBot(new HealAction(owner, owner, 3));
                    break;
            }
        } else if (amount > 10) {
            int randEffect = rand.nextInt(2);
            switch (randEffect) {
                case 0:
                    this.addToBot(new GainBlockAction(owner, 3));
                    break;
                case 1:
                    this.addToBot(new HealAction(owner, owner, 2));
                    break;
            }
        } else if (amount >= 0) {
            int randEffect = rand.nextInt(2);
            switch (randEffect) {
                case 0:
                    this.addToBot(new GainBlockAction(owner, 2));
                    break;
                case 1:
                    this.addToBot(new HealAction(owner, owner, 1));
                    break;
            }
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        this.flash();
        if (!card.hasTag(HumanMod.HumanCardTags.JUNK_HUMAN)) {
            if (amount > 18) {
                int randEffect = rand.nextInt(2);
                switch (randEffect) {
                    case 0:
                        this.addToBot(new DamageRandomEnemyAction(
                            new DamageInfo(owner, (amount - 10) / 3,
                                           DamageInfo.DamageType.NORMAL),
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                        break;
                    case 1:
                        this.addToBot(
                            new GainBlockAction(owner, (amount - 10) / 3));
                        break;
                }
                int randEffect2 = rand.nextInt(2);
                switch (randEffect2) {
                    case 0:
                        this.addToBot(new HealAction(owner, owner, 2));
                        break;
                    case 1:
                        if (rand.nextInt(3) <= 1) {
                            this.addToBot(new DrawCardAction(1));
                        }
                        break;
                }
            } else if (amount > 10) {
                int randEffect = rand.nextInt(4);
                switch (randEffect) {
                    case 0:
                        this.addToBot(new DamageRandomEnemyAction(
                            new DamageInfo(owner, 1,
                                           DamageInfo.DamageType.NORMAL),
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                        break;
                    case 1:
                        this.addToBot(new GainBlockAction(owner, 2));
                        break;
                    case 2:
                        this.addToBot(new HealAction(owner, owner, 1));
                        break;
                    case 3:
                        if (rand.nextBoolean()) {
                            this.addToBot(new DrawCardAction(1));
                        }
                        break;
                }
            } else if (amount >= 0) {
                int randEffect = rand.nextInt(2);
                switch (randEffect) {
                    case 0:
                        this.addToBot(new GainBlockAction(owner, 1));
                        break;
                    case 1:
                        if (rand.nextBoolean()) {
                            this.addToBot(new HealAction(owner, owner, 1));
                        }
                        break;
                    default:
                }
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HealthinessPower(owner, source, amount);
    }
}
