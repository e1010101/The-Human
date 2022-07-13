package theHuman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.tokens.*;
import theHuman.characters.TheHuman;
import theHuman.util.HumanUtils;

import static theHuman.HumanMod.makeCardPath;

public class DoItYourself extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(DoItYourself.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("DoItYourself.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;

    public DoItYourself() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                             AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (HumanUtils.getCardIDs(p.hand).contains(SmallRock.ID) ||
            HumanUtils.getCardIDs(p.hand).contains(Dandelion.ID) ||
            HumanUtils.getCardIDs(p.hand).contains(SodaCan.ID) ||
            HumanUtils.getCardIDs(p.hand).contains(OldShoes.ID) ||
            HumanUtils.getCardIDs(p.hand).contains(PlasticBottle.ID) ||
            HumanUtils.getCardIDs(p.hand).contains(PaperBag.ID)) {
            this.addToBot(new GainEnergyAction(magicNumber));
            this.addToBot(new DrawCardAction(magicNumber));
        }
    }
}
