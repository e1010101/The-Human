package theHuman.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import theHuman.HumanMod;
import theHuman.cards.tokens.EmptyCartridge;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractShootWeaponCard extends AbstractDefaultCard {

	public AbstractShootWeaponCard(final String id, final String img, final int cost, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {

		super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
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

}
