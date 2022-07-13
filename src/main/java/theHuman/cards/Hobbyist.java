package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.util.HumanUtils;

import static theHuman.HumanMod.makeCardPath;

public class Hobbyist extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Hobbyist.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("Hobbyist.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public Hobbyist() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
    }

    public boolean shouldGlowGold() {
        return HumanUtils.countUniqueCards(AbstractDungeon.player.hand) ==
               AbstractDungeon.player.hand.size();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (HumanUtils.checkHandForDuplicates(p)) {
            this.addToBot(new DrawCardAction(magicNumber * 3));
            if (upgraded) {
                this.addToBot(new GainEnergyAction(1));
            }
        } else {
            this.addToBot(new DrawCardAction(magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor =
            shouldGlowGold() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() :
            AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
