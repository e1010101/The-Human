package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theHuman.HumanMod;
import theHuman.patches.UncoveredField;
import theHuman.util.HumanUtils;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class ArsenalPower extends AbstractPower implements CloneablePowerInterface {

	public static final String POWER_ID = HumanMod.makeID("ArsenalPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ArsenalPower_84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ArsenalPower_32.png"));
	public AbstractCreature source;

	public ArsenalPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
			description = DESCRIPTIONS[0];
		} else if (amount > 1) {
			description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
		}
	}

	@Override
	public void atStartOfTurnPostDraw() {
		this.flash();
		AbstractCard c = HumanUtils.getRandomWeapon();
		c.setCostForTurn(0);
		c.isCostModified = true;
		c.exhaust = true;
		UncoveredField.uncovered.set(c, true);
		AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
	}

	@Override
	public AbstractPower makeCopy() {
		return new ArsenalPower(owner, source, amount);
	}
}
