package theHuman.cards.tokens;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;

import static theHuman.HumanMod.makeCardPath;

public class Poverty extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Poverty.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("Poverty.png");

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;

    private static final int COST = -2;

    public Poverty() {
        super(ID, IMG, COST, TYPE, CardColor.CURSE, RARITY, TARGET);
        this.isEthereal = true;
        this.dontTriggerOnUseCard = true;
    }

    @Override
    public void upgrade() {
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToBot(
            new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player,
                             3));
        this.addToBot(new SetDontTriggerAction(this, false));
    }
}
