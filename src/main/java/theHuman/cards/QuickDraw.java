package theHuman.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.DrawWeaponAction;
import theHuman.actions.FireGunRandomlyAction;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class QuickDraw extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(QuickDraw.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("QuickDraw.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;

    private static final int DAMAGE = 1;

    public QuickDraw() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 5;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 4;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawWeaponAction(1));
        if (upgraded) {
            this.addToBot(
                new FireGunRandomlyAction(damage, defaultSecondMagicNumber,
                                          magicNumber));
        }
    }
}
