package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;

public class AddRandomCurseToDeckAction extends AbstractGameAction {

    private static final CardGroup allCurses;

    static {
        allCurses = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allCurses.addToTop(new Clumsy());
        allCurses.addToTop(new Decay());
        allCurses.addToTop(new Doubt());
        allCurses.addToTop(new Injury());
        allCurses.addToTop(new Pain());
        allCurses.addToTop(new Parasite());
        allCurses.addToTop(new Slimed());
        allCurses.sortAlphabetically(false);
    }

    private ArrayList<AbstractCard> cardsToShuffle = new ArrayList<>();

    public AddRandomCurseToDeckAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    private static CardGroup allCursesToUse() {
        return allCurses;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            cardsToShuffle = getRandomCurse(amount);
            findCurse();
        }
        this.tickDuration();
    }

    private void findCurse() {
        for (AbstractCard c : cardsToShuffle) {
            c.unhover();
            AbstractDungeon.effectList.add(
                new ShowCardAndAddToDrawPileEffect(c.makeStatEquivalentCopy(),
                                                   false, false));
        }
        cardsToShuffle.clear();
    }

    private AbstractCard getRandomCurse() {
        return allCursesToUse().getRandomCard(false);
    }

    private ArrayList<AbstractCard> getRandomCurse(int amount) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < amount) {
            AbstractCard card = getRandomCurse();
            cards.add(card);
        }
        return cards;
    }
}
