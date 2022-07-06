package theHuman.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theHuman.cards.guns.*;
import theHuman.cards.tokens.*;

import java.util.*;

public class HumanUtils {

    private static final CardGroup allJunk;
    private static final CardGroup allWeapons;

    static {
        allJunk = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allJunk.addToTop(new SmallRock());
        allJunk.addToTop(new Dandelion());
        allJunk.addToTop(new SodaCan());
        allJunk.addToTop(new OldShoes());
        allJunk.addToTop(new PlasticBottle());
        allJunk.addToTop(new PaperBag());
        allJunk.sortAlphabetically(false);
    }

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
        allWeapons.sortAlphabetically(false);
    }

    public static boolean checkHandForDuplicates(AbstractPlayer owner) {
        Map<String, Integer> countsByCardID = new HashMap<>();
        boolean noDuplicates = true;
        for (AbstractCard card : owner.hand.group) {
            String id = card.cardID;
            int count = countsByCardID.getOrDefault(id, 0);
            if (count == 1) {
                noDuplicates = false;
            }
            countsByCardID.put(id, count + 1);
        }
        return noDuplicates;
    }

    public static boolean checkHandForAttacks(AbstractPlayer owner) {
        for (AbstractCard card : owner.hand.group) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                return true;
            }
        }
        return false;
    }

    public static int countUniqueCards(CardGroup group) {
        Set<String> uniqueCardIDs = new HashSet<>();
        group.group.forEach(c -> uniqueCardIDs.add(c.cardID));
        return uniqueCardIDs.size();
    }

    public static AbstractCard chooseRandomJunk() {
        return allJunk.getRandomCard(false);
    }

    public static AbstractCard getRandomWeapon() {
        return allWeapons.getRandomCard(false);
    }

    public static ArrayList<String> getCardIDs(CardGroup group) {
        ArrayList<String> retVal = new ArrayList<>();

        for (AbstractCard card : group.group) {
            retVal.add(card.cardID);
        }

        return retVal;
    }
}
