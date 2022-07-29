package theHuman.cards.guns;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class DesertEagle extends AbstractShootWeaponCard {

    public static final String ID =
        HumanMod.makeID(DesertEagle.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    private static final String MASTERED_NAME =
        cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG = makeCardPath("DesertEagle.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    // Credit to Alexander Yartsev for image, https://www.artstation.com/artwork/v1Y12A
    public static final String MASTERED_IMG =
        makeCardPath("DesertEagle_Mastered" + ".png");
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":MasteryWords");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int UPGRADE_PLUS_SECOND_MAGIC = 4;
    private static final int MASTERY_LEVEL = 9;
    private static final String MASTERED_SMALL =
        getModID() + "Resources/images/cards/bonus/circuit.png";
    private static final String MASTERED_LARGE =
        getModID() + "Resources/images/cards/bonus/circuit_p.png";
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#c20000]" + uiStrings.TEXT[0],
                        "[#c20000]" + uiStrings.TEXT[1]);
    private static final List<TooltipInfo> tooltips =
        Collections.singletonList(toolTipInfo);

    public DesertEagle() {
        this(0);
    }

    public DesertEagle(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, tooltips);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 33;
        timesUpgraded = upgrades;
    }

    @Override
    public void upgrade() {
        timesUpgraded++;
        upgradeDamage(UPGRADE_PLUS_DMG);
        if (defaultSecondMagicNumber + UPGRADE_PLUS_SECOND_MAGIC > 100) {
            upgradeDefaultSecondMagicNumber(100 - defaultSecondMagicNumber);
        } else {
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC);
        }
        upgraded = true;
        if (this.timesUpgraded >= MASTERY_LEVEL) {
            name = MASTERED_NAME;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            this.textureImg = MASTERED_IMG;
            this.loadCardImage(MASTERED_IMG);
            this.setBackgroundTexture(MASTERED_SMALL, MASTERED_LARGE);
            this.customTooltips = new ArrayList<>();
        } else {
            name = cardStrings.NAME + " + " + timesUpgraded;
        }
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        selfRetain = false;
        CardCrawlGame.sound.playAV("desertEagle", 1.0F, 0.6F);
        this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber,
                                        magicNumber));
        this.addToBot(
            new MakeTempCardInDrawPileAction(new EmptyCartridge(), magicNumber,
                                             true, true));
        if (timesUpgraded >= MASTERY_LEVEL) {
            masteryEffect();
        }
    }

    @Override
    public void triggerWhenDrawn() {
        selfRetain = true;
    }

    @Override
    public void masteryEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) {
            if (!Objects.equals(c.cardID, this.cardID)) {
                this.addToBot(new ExhaustSpecificCardAction(c, p.hand, true));
            }
        }
        this.addToBot(new HealAction(p, p, 1000));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DesertEagle();
    }

    @Override
    public float getTitleFontSize() {
        return timesUpgraded >= MASTERY_LEVEL ? 20.0F : 27.0F;
    }
}
