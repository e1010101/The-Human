package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class AggressivenessPower extends AbstractPower implements CloneablePowerInterface {
	public static final String POWER_ID = HumanMod.makeID("AggressivenessPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("AggressivenessPower_84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("AggressivenessPower_32.png"));
	public AbstractCreature source;

	public AggressivenessPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
		if (amount == 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
		} else if (amount > 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
		}
	}

	@Override
	public void atStartOfTurnPostDraw() {
		this.flash();
		this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
		this.addToBot(new DiscardAction(owner, owner, amount, false));
	}

	@Override
	public AbstractPower makeCopy() {
		return new AggressivenessPower(owner, source, amount);
	}
}
