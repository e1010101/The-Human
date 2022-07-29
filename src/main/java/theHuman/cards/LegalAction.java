package theHuman.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.tokens.Poverty;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class LegalAction extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(LegalAction.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("LegalAction.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int DAMAGE = 25;
    private static final int UPGRADE_PLUS_DAMAGE = 10;

    private static final int BLOCK = 25;
    private static final int UPGRADE_PLUS_BLOCK = 10;

    public LegalAction() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        block = baseBlock = BLOCK;
        cardsToPreview = new Poverty();
        ExhaustiveVariable.setBaseValue(this, 2);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                             AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new GainBlockAction(p, p, block));
        this.addToBot(
            new MakeTempCardInDrawPileAction(new Poverty(), 1, true, true));
    }
}
