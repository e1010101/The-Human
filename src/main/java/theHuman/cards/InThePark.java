package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import theHuman.HumanMod;
import theHuman.actions.FindJunkAction;
import theHuman.characters.TheHuman;
import theHuman.patches.subscribers.OnShuffleSubscriber;
import theHuman.powers.HealthinessPower;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class InThePark extends AbstractDynamicCard
    implements OnShuffleSubscriber {

    public static final String ID =
        HumanMod.makeID(InThePark.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":LifestyleWords");
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("InThePark.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#2000a7]" + uiStrings.TEXT[0], uiStrings.TEXT[1]);

    public InThePark() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new HealthinessPower(p, p,
                                                                      magicNumber)));
        this.addToBot(
            new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
        this.addToBot(new DrawCardAction(1));
    }

    @Override
    public void onShuffle() {
        this.addToBot(new FindJunkAction(defaultSecondMagicNumber));
    }
}
