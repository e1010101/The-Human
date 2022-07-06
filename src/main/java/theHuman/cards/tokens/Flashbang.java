package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class Flashbang extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(Flashbang.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Flashbang.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 0;

	public Flashbang() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		magicNumber = baseMagicNumber = 1;
		selfRetain = true;
		exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new StunMonsterAction(m, p));
	}
}
