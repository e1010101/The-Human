package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.Comparator;
import java.util.List;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;

public class ObtainTempCopyOfAnyCardAction extends AbstractGameAction {

    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString("BetterToHandAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private static final Comparator<AbstractCard> BY_COLOR =
        Comparator.comparing(card -> card.color);
    private static final Comparator<AbstractCard> BY_NAME =
        Comparator.comparing(card -> card.name);
    private boolean retrieveCard = false;

    public ObtainTempCopyOfAnyCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    @Override
    public void update() {
        final CardGroup cards =
            new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        final List<AbstractCard> pool = CardLibrary.getAllCards();
        pool.removeIf(c -> c.type == CURSE || c.type == STATUS);
        pool.sort(BY_COLOR.thenComparing(BY_NAME));
        pool.forEach(cards::addToTop);

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.gridSelectScreen.open(cards, 1, TEXT[0], false,
                                                  false, true, false);
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.gridSelectScreen.selectedCards != null) {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        AbstractCard disCard = c.makeStatEquivalentCopy();
                        AbstractCard disCard2 = c.makeStatEquivalentCopy();
                        disCard.upgrade();
                        disCard2.upgrade();
                        disCard.cost = 0;
                        disCard.costForTurn = 0;
                        disCard2.cost = 0;
                        disCard2.costForTurn = 0;
                        disCard.current_x = -1000.0F * Settings.xScale;
                        disCard2.current_x = -1000.0F * Settings.xScale +
                                             AbstractCard.IMG_HEIGHT_S;
                        if (this.amount == 1) {
                            if (AbstractDungeon.player.hand.size() < 10) {
                                AbstractDungeon.effectList.add(
                                    new ShowCardAndAddToHandEffect(disCard,
                                                                   (float) Settings.WIDTH /
                                                                   2.0F,
                                                                   (float) Settings.HEIGHT /
                                                                   2.0F));
                            } else {
                                AbstractDungeon.effectList.add(
                                    new ShowCardAndAddToDiscardEffect(disCard,
                                                                      (float) Settings.WIDTH /
                                                                      2.0F,
                                                                      (float) Settings.HEIGHT /
                                                                      2.0F));
                            }

                        } else if (
                            AbstractDungeon.player.hand.size() + this.amount <=
                            10) {
                            AbstractDungeon.effectList.add(
                                new ShowCardAndAddToHandEffect(disCard,
                                                               (float) Settings.WIDTH /
                                                               2.0F -
                                                               AbstractCard.IMG_WIDTH /
                                                               2.0F,
                                                               (float) Settings.HEIGHT /
                                                               2.0F));
                            AbstractDungeon.effectList.add(
                                new ShowCardAndAddToHandEffect(disCard2,
                                                               (float) Settings.WIDTH /
                                                               2.0F +
                                                               AbstractCard.IMG_WIDTH /
                                                               2.0F,
                                                               (float) Settings.HEIGHT /
                                                               2.0F));
                        } else if (AbstractDungeon.player.hand.size() == 9) {
                            AbstractDungeon.effectList.add(
                                new ShowCardAndAddToHandEffect(disCard,
                                                               (float) Settings.WIDTH /
                                                               2.0F -
                                                               AbstractCard.IMG_WIDTH /
                                                               2.0F,
                                                               (float) Settings.HEIGHT /
                                                               2.0F));
                            AbstractDungeon.effectList.add(
                                new ShowCardAndAddToDiscardEffect(disCard2,
                                                                  (float) Settings.WIDTH /
                                                                  2.0F +
                                                                  AbstractCard.IMG_WIDTH /
                                                                  2.0F,
                                                                  (float) Settings.HEIGHT /
                                                                  2.0F));
                        } else {
                            AbstractDungeon.effectList.add(
                                new ShowCardAndAddToDiscardEffect(disCard,
                                                                  (float) Settings.WIDTH /
                                                                  2.0F -
                                                                  AbstractCard.IMG_WIDTH /
                                                                  2.0F,
                                                                  (float) Settings.HEIGHT /
                                                                  2.0F));
                            AbstractDungeon.effectList.add(
                                new ShowCardAndAddToDiscardEffect(disCard2,
                                                                  (float) Settings.WIDTH /
                                                                  2.0F +
                                                                  AbstractCard.IMG_WIDTH /
                                                                  2.0F,
                                                                  (float) Settings.HEIGHT /
                                                                  2.0F));
                        }
                    }
                }
                this.retrieveCard = true;
            }
        }
        this.tickDuration();
    }
}