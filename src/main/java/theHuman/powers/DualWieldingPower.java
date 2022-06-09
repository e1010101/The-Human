package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class DualWieldingPower extends AbstractPower implements CloneablePowerInterface {
	public static final String POWER_ID = HumanMod.makeID("DualWieldingPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DualWieldingPower_84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DualWieldingPower_32.png"));
	public AbstractCreature source;

	public DualWieldingPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (card.hasTag(HumanMod.HumanCardTags.SHOOTER_HUMAN)) {
			this.flash();
			this.addToBot(new FireGunAction(AbstractDungeon.getRandomMonster(), 15, 20, amount));
		}
	}

	@Override
	public AbstractPower makeCopy() {
		return new DualWieldingPower(owner, source, amount);
	}
}
