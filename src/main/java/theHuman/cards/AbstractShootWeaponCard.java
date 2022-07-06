package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import theHuman.HumanMod;
import theHuman.cards.tokens.EmptyCartridge;

import java.util.List;

public abstract class AbstractShootWeaponCard extends AbstractDynamicCard {

    public AbstractShootWeaponCard(final String id, final String img,
                                   final int cost, final CardType type,
                                   final CardColor color,
                                   final CardRarity rarity,
                                   final CardTarget target,
                                   final List<TooltipInfo> tooltips) {

        super(id, img, cost, type, color, rarity, target, tooltips);
        selfRetain = true;
        returnToHand = true;
        cardsToPreview = new EmptyCartridge();
        this.tags.add(HumanMod.HumanCardTags.WEAPON_HUMAN);
        this.tags.add(HumanMod.HumanCardTags.SHOOTER_HUMAN);
        CommonKeywordIconsField.useIcons.set(this, Boolean.TRUE);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    public abstract void masteryEffect();
}
