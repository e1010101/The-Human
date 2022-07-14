package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.cards.tokens.BigBrain;
import theHuman.cards.tokens.DeepThought;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class ReadingPower extends AbstractPower
    implements CloneablePowerInterface {
    public static final String POWER_ID = HumanMod.makeID("ReadingPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("ReadingPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("ReadingPower_32.png"));
    private final boolean upgraded;
    public AbstractCreature source;

    public ReadingPower(final AbstractCreature owner,
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
            description = DESCRIPTIONS[0] + amount +
                          (upgraded ? DESCRIPTIONS[3] : DESCRIPTIONS[1]);
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount +
                          (upgraded ? DESCRIPTIONS[4] : DESCRIPTIONS[2]);
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.cost >= 2) {
            this.flash();
            this.addToBot(new MakeTempCardInHandAction(
                upgraded ? new BigBrain() : new DeepThought(), amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ReadingPower(owner, source, amount, upgraded);
    }
}
