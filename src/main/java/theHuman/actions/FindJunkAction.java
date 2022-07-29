package theHuman.actions;

import basemod.BaseMod;
import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theHuman.cards.tokens.*;

import java.util.ArrayList;

public class FindJunkAction extends AbstractGameAction {

    private static final CardGroup allJunk;
    private static final RandomXS128 rand = new RandomXS128();

    static {
        allJunk = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allJunk.addToTop(new SmallRock());      // 25%
        allJunk.addToTop(new Dandelion());      // 20%
        allJunk.addToTop(new SodaCan());        // 15%
        allJunk.addToTop(new OldShoes());       // 20%
        allJunk.addToTop(new PlasticBottle());  // 15%
        allJunk.addToTop(new PaperBag());       // 5%
        allJunk.sortAlphabetically(false);
    }

    private ArrayList<AbstractCard> cardsToShuffle = new ArrayList<>();

    public FindJunkAction(int numOfJunk) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = numOfJunk;
    }

    private static CardGroup allJunkToUse() {
        return allJunk;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            cardsToShuffle = getRandomJunk(amount);
            findJunk();
        }
        this.tickDuration();
    }

    private void findJunk() {
        for (AbstractCard c : cardsToShuffle) {
            c.unhover();
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.effectList.add(
                    new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
            } else {
                AbstractDungeon.effectList.add(
                    new ShowCardAndAddToDiscardEffect(
                        c.makeStatEquivalentCopy()));
            }
        }
        cardsToShuffle.clear();
    }

    private AbstractCard getRandomJunk() {
        int randomNum = rand.nextInt(100);
        if (randomNum < 25) {
            return allJunkToUse().findCardById(SmallRock.ID);
        } else if (randomNum < 45) {
            return allJunkToUse().findCardById(Dandelion.ID);
        } else if (randomNum < 60) {
            return allJunkToUse().findCardById(SodaCan.ID);
        } else if (randomNum < 80) {
            return allJunkToUse().findCardById(OldShoes.ID);
        } else if (randomNum < 95) {
            return allJunkToUse().findCardById(PlasticBottle.ID);
        } else {
            return allJunkToUse().findCardById(PaperBag.ID);
        }
    }

    private ArrayList<AbstractCard> getRandomJunk(int amount) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < amount) {
            AbstractCard card = getRandomJunk();
            cards.add(card);
        }
        return cards;
    }
}
