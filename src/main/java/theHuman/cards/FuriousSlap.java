package theHuman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.InjuredPower;

import static theHuman.HumanMod.makeCardPath;

public class FuriousSlap extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(FuriousSlap.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("FuriousSlap.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 3;

    public FuriousSlap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
        this.tags.add(HumanMod.HumanCardTags.SLAP_HUMAN);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(m, 1)));
        this.addToBot(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                             AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(
            new ApplyPowerAction(m, m, new InjuredPower(m, m, magicNumber),
                                 magicNumber));
    }
}
