package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.actions.PlayGhostCopyOfCardAction;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class FumingPower extends AbstractPower
    implements CloneablePowerInterface {
    public static final String POWER_ID = HumanMod.makeID("FumingPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("FumingPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("FumingPower_32.png"));
    private final boolean upgraded;
    public AbstractCreature source;

    public FumingPower(final AbstractCreature owner,
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
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] +
                          (upgraded ? DESCRIPTIONS[2] : DESCRIPTIONS[1]);
        } else if (amount > 1) {
            if (upgraded) {
                description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + amount +
                              DESCRIPTIONS[5] + amount + DESCRIPTIONS[6];
            } else {
                description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + amount +
                              DESCRIPTIONS[4];
            }
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.tags.contains(HumanMod.HumanCardTags.SLAP_HUMAN)) {
            this.flash();
            this.addToBot(new PlayGhostCopyOfCardAction(card));
            if (upgraded) {
                this.addToBot(new DrawCardAction(amount));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FumingPower(owner, source, amount, upgraded);
    }
}
