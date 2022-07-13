package theHuman.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.ShuffleHandAction;
import theHuman.characters.TheHuman;
import theHuman.util.HumanUtils;

import static theHuman.HumanMod.makeCardPath;

public class Recycle extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Recycle.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Recycle.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public Recycle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 5;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(
                new MakeTempCardInDrawPileAction(HumanUtils.chooseRandomJunk(),
                                                 1, true, true, false,
                                                 (float) Settings.WIDTH / 2.0F,
                                                 (float) Settings.HEIGHT /
                                                 2.0F));
        }
        int count = AbstractDungeon.player.hand.size();
        this.addToBot(new ShuffleHandAction());
        this.addToBot(new DrawCardAction(count));
    }
}
