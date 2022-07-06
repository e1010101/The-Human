package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHuman.HumanMod;
import theHuman.actions.FireGunRandomlyAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.characters.TheHuman;

import java.util.ArrayList;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class Revolver extends AbstractShootWeaponCard {

	public static final String ID = HumanMod.makeID(Revolver.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.NAME;
	public static final String IMG = makeCardPath("Revolver.png");
	public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	private static final int COST = 1;
	private static final int DAMAGE = 9;

	public Revolver() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, new ArrayList<>());
		damage = baseDamage = DAMAGE;
		magicNumber = baseMagicNumber = 1;
		defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 8;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		selfRetain = false;
		this.addToBot(new FireGunRandomlyAction(damage, defaultSecondMagicNumber, magicNumber));
		this.addToBot(new MakeTempCardInDrawPileAction(new EmptyCartridge(), magicNumber, true, true));
	}

	@Override
	public void triggerWhenDrawn() {
		selfRetain = true;
	}

	@Override
	public void masteryEffect() {
	}
}
