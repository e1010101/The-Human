package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;

import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    protected List<TooltipInfo> customTooltips;

    public AbstractDynamicCard(final String id, final String img,
                               final int cost, final CardType type,
                               final CardColor color, final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost,
              languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity,
              target);
        CommonKeywordIconsField.useIcons.set(this, Boolean.TRUE);
    }

    public AbstractDynamicCard(final String id, final String img,
                               final int cost, final String desc, final CardType type,
                               final CardColor color, final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost,
              desc, type, color, rarity,
              target);
        CommonKeywordIconsField.useIcons.set(this, Boolean.TRUE);
    }

    public AbstractDynamicCard(final String id, final String img,
                               final int cost, final CardType type,
                               final CardColor color, final CardRarity rarity,
                               final CardTarget target,
                               final List<TooltipInfo> tooltips) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost,
              languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity,
              target);
        CommonKeywordIconsField.useIcons.set(this, Boolean.TRUE);
        customTooltips = tooltips;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (customTooltips == null) {
            customTooltips = super.getCustomTooltips();
        }
        return customTooltips;
    }
}