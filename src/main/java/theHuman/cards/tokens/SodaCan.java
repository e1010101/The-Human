package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class SodaCan extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(SodaCan.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("SodaCan.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	private static final int COST = 0;

	public SodaCan() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		purgeOnUse = true;
		this.tags.add(HumanMod.HumanCardTags.JUNK_HUMAN);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new GainEnergyAction(1));
	}
}
