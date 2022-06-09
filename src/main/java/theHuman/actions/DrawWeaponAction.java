package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHuman.HumanMod;

import java.util.Iterator;

public class DrawWeaponAction extends AbstractGameAction {

	private final AbstractPlayer p = AbstractDungeon.player;
	private final int numOfWeapons;
	private boolean actionDone = false;

	public DrawWeaponAction(int numToDraw) {
		this.numOfWeapons = numToDraw;
		this.duration = Settings.ACTION_DUR_MED;
	}

	@Override
	public void update() {
		if (!actionDone) {

			if (p.drawPile.isEmpty()) {
				this.isDone = true;
				return;
			}

			CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			Iterator<AbstractCard> var2 = p.drawPile.group.iterator();
			AbstractCard card;
			while (var2.hasNext()) {
				card = var2.next();
				if (card.hasTag(HumanMod.HumanCardTags.WEAPON_HUMAN) && card.hasTag(HumanMod.HumanCardTags.SHOOTER_HUMAN)) {
					tmp.addToRandomSpot(card);
				}
			}

			if (tmp.size() == 0) {
				this.isDone = true;
				return;
			}

			if (!tmp.isEmpty()) {
				tmp.shuffle();
				card = tmp.getBottomCard();
				tmp.removeCard(card);

				if (p.hand.size() == 10) {
					p.drawPile.moveToDiscardPile(card);
					p.createHandIsFullDialog();
				} else {
					card.unhover();
					card.lighten(true);
					card.setAngle(0.0F);
					card.drawScale = 0.12F;
					card.targetDrawScale = 0.75F;
					card.current_x = CardGroup.DRAW_PILE_X;
					card.current_y = CardGroup.DRAW_PILE_Y;
					p.drawPile.removeCard(card);
					AbstractDungeon.player.hand.addToTop(card);
					AbstractDungeon.player.hand.refreshHandLayout();
					AbstractDungeon.player.hand.applyPowers();
				}
			}

			actionDone = true;
		}
		this.tickDuration();
	}
}
