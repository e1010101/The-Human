package theHuman.cards.guns;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.FireGunRandomlyAction;
import theHuman.cards.AbstractDynamicCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;

import java.util.Objects;

import static theHuman.HumanMod.makeCardPath;

public class Speedloader extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Speedloader.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Speedloader.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;

    public Speedloader() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer,
                    AbstractMonster abstractMonster) {
        for (AbstractCard c : abstractPlayer.drawPile.group) {
            if (Objects.equals(c.cardID, EmptyCartridge.ID)) {
                this.addToBot(
                    new ExhaustSpecificCardAction(c, abstractPlayer.drawPile));
            }
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(HumanMod.HumanCardTags.SHOOTER_HUMAN)) {
            this.flash(Color.ORANGE.cpy());
            this.addToBot(new FireGunRandomlyAction(4, 5, 2));
        }
    }
}
