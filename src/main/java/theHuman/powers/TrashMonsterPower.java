package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHuman.HumanMod;
import theHuman.actions.FindJunkAction;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class TrashMonsterPower extends TwoAmountPower
    implements CloneablePowerInterface {

    public static final String POWER_ID = HumanMod.makeID("TrashMonsterPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("TrashMonsterPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("TrashMonsterPower_32.png"));
    private final int limit;
    public AbstractCreature source;

    public TrashMonsterPower(final AbstractCreature owner,
                             final AbstractCreature source, final int amount,
                             final int amount2, final int limit) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        this.source = source;

        this.limit = Math.max(6 - limit, 1);

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + limit +
                      DESCRIPTIONS[2] + amount * 5 + DESCRIPTIONS[3] + amount +
                      DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new FindJunkAction(amount));
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.tags.contains(HumanMod.HumanCardTags.JUNK_HUMAN)) {
            this.flash();
            amount2++;
            if (amount2 >= limit) {
                AbstractPlayer p = AbstractDungeon.player;
                this.addToBot(new DamageRandomEnemyAction(
                    new DamageInfo(AbstractDungeon.player, 5,
                                   DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                this.addToBot(
                    new ApplyPowerAction(p, p, new StrengthPower(p, amount)));
                this.addToBot(
                    new ApplyPowerAction(p, p, new DexterityPower(p, amount)));
                amount2 = 0;
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrashMonsterPower(owner, source, amount, amount2, limit);
    }
}
