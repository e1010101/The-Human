package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.HealthinessPower;

import static theHuman.HumanMod.makeCardPath;

public class Detoxification extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Detoxification.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Detoxification.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;

	private static final int BLOCK = 3;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Detoxification() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		block = baseBlock = BLOCK;
		magicNumber = baseMagicNumber = 1;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new HealthinessPower(p, p, magicNumber), magicNumber));
		this.addToBot(new GainBlockAction(p, p, block));
		for (int i = 0; i < p.drawPile.size(); ) {
			AbstractCard c = p.drawPile.group.get(i);
			if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
				p.drawPile.removeCard(c);
				p.limbo.addToTop(c);
				c.targetDrawScale = 0.5F;
				c.setAngle(0, true);
				c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
				c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
				this.addToBot(new ExhaustSpecificCardAction(c, p.limbo));
				this.addToBot(new WaitAction(0.1F));
			} else {
				i++;
			}
		}

		for (int i = 0; i < p.discardPile.size(); ) {
			AbstractCard c = p.discardPile.group.get(i);
			if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
				p.discardPile.removeCard(c);
				p.limbo.addToTop(c);
				c.targetDrawScale = 0.5F;
				c.setAngle(0, true);
				c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
				c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
				this.addToBot(new ExhaustSpecificCardAction(c, p.limbo));
				this.addToBot(new WaitAction(0.1F));
			} else {
				i++;
			}
		}

		for (int i = 0; i < p.hand.size(); ) {
			AbstractCard c = p.hand.group.get(i);
			if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
				p.hand.removeCard(c);
				p.limbo.addToTop(c);
				c.targetDrawScale = 0.5F;
				c.setAngle(0, true);
				c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
				c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
				this.addToBot(new ExhaustSpecificCardAction(c, p.limbo));
				this.addToBot(new WaitAction(0.1F));
			} else {
				i++;
			}
		}
	}
}
