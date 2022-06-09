package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class PepperSpray extends AbstractDynamicCard {

	public static final String ID = HumanMod.makeID(PepperSpray.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG = makeCardPath("PepperSpray.png");

	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 0;

	private static final int DAMAGE = 3;

	public PepperSpray() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 6;
		this.tags.add(HumanMod.HumanCardTags.WEAPON_HUMAN);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
		this.addToBot(new ApplyPowerAction(m, m, new PoisonPower(m, m, magicNumber)));
		this.addToBot(new ApplyPowerAction(m, m, new WeakPower(m, magicNumber, false)));
		this.addToBot(new ApplyPowerAction(m, m, new VulnerablePower(m, magicNumber, false)));
	}
}
