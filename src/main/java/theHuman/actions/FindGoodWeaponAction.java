package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theHuman.cards.guns.*;
import theHuman.cards.tokens.LugerPistol;
import theHuman.cards.tokens.MK3A2Grenade;
import theHuman.cards.tokens.PocketPistol;
import theHuman.cards.tokens.Revolver;

import java.util.ArrayList;

public class FindGoodWeaponAction extends AbstractGameAction {

    private static final CardGroup allWeapons;

    static {
        allWeapons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allWeapons.addToTop(new Revolver());
        allWeapons.addToTop(new LugerPistol());
        allWeapons.addToTop(new MK3A2Grenade());
        allWeapons.addToTop(new AK47());
        allWeapons.addToTop(new DesertEagle());
        allWeapons.addToTop(new PocketPistol());
        allWeapons.addToTop(new AWP());
        allWeapons.addToTop(new Berreta92());
        allWeapons.addToTop(new Glock17());
    }

    private ArrayList<AbstractCard> cardsToShuffle = new ArrayList<>();

    public FindGoodWeaponAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    private static CardGroup allWeaponsToUse() {
        return allWeapons;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            cardsToShuffle = getRandomWeapon(amount);
            findWeapon();
        }
        this.tickDuration();
    }

    private void findWeapon() {
        for (AbstractCard c : cardsToShuffle) {
            c.unhover();
            c.exhaust = true;
            if (cardsToShuffle.size() < 6) {
                AbstractDungeon.effectList.add(
                    new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy(),
                                                   (float) Settings.WIDTH /
                                                   2.0f,
                                                   (float) Settings.HEIGHT /
                                                   2.0f));
            } else {
                AbstractDungeon.effectList.add(
                    new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
            }
        }
        cardsToShuffle.clear();
    }

    private AbstractCard getRandomWeapon() {
        return allWeaponsToUse().getRandomCard(false);
    }

    private ArrayList<AbstractCard> getRandomWeapon(int amount) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < amount) {
            AbstractCard card = getRandomWeapon();
            cards.add(card);
        }
        return cards;
    }
}
