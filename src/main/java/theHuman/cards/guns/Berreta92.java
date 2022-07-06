package theHuman.cards.guns;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class Berreta92 extends AbstractShootWeaponCard {

    public static final String ID =
        HumanMod.makeID(Berreta92.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = makeCardPath("Berreta92.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 11;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int UPGRADE_PLUS_SECOND_MAGIC = 2;
    private static final int MASTERY_LEVEL = 7;
    private static final String MASTERED_NAME =
        cardStrings.EXTENDED_DESCRIPTION[0];
    private static final String MASTERED_SMALL =
        getModID() + "Resources/images/cards/bonus/gunmetal.png";
    private static final String MASTERED_LARGE =
        getModID() + "Resources/images/cards/bonus/gunmetal_p.png";
    public static final String MASTERED_IMG =
        makeCardPath("Berreta92_Mastered" + ".png");
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#c20000]Mastery",
                        "[#c20000]Mastery X: Can be upgraded any " +
                        "number of times. NL NL Upon reaching X " +
                        "upgrades, obtain a new form.");
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public Berreta92() {
        this(0);
    }

    public Berreta92(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 10;
        timesUpgraded = upgrades;
    }

    @Override
    public void upgrade() {
        timesUpgraded++;
        upgradeDamage(UPGRADE_PLUS_DMG);
        if (defaultSecondMagicNumber + UPGRADE_PLUS_SECOND_MAGIC > 100) {
            defaultSecondMagicNumber = 100;
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
        } else {
            name = cardStrings.NAME + " + " + timesUpgraded;
        }
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        selfRetain = false;
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
        this.addToBot(new VFXAction(
            new ShowCardBrieflyEffect(this.makeStatEquivalentCopy())));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(
                new FireGunAction(AbstractDungeon.getRandomMonster(), damage,
                                  100, magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Berreta92();
    }
}
