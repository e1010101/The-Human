package theHuman.cards.guns;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;
import theHuman.effects.CustomSparksEffect;

import java.util.Arrays;
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
    private static final String MASTERED_NAME =
        cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG = makeCardPath("Berreta92.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    public static final String MASTERED_IMG =
        makeCardPath("Berreta92_Mastered" + ".png");
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":MasteryWords");
    private static final UIStrings uiStrings2 =
        CardCrawlGame.languagePack.getUIString(getModID() + ":Berreta92Words");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 11;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int UPGRADE_PLUS_SECOND_MAGIC = 2;
    private static final int MASTERY_LEVEL = 7;
    private static final String MASTERED_SMALL =
        getModID() + "Resources/images/cards/bonus/gunmetal.png";
    private static final String MASTERED_LARGE =
        getModID() + "Resources/images/cards/bonus/gunmetal_p.png";
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#c20000]" + uiStrings.TEXT[0],
                        "[#c20000]" + uiStrings.TEXT[1]);
    private static final List<TooltipInfo> tooltips =
        Collections.singletonList(toolTipInfo);
    private static final TooltipInfo toolTipInfo2 =
        new TooltipInfo(uiStrings2.TEXT[0], uiStrings2.TEXT[1]);
    private static final List<String> powers =
        Arrays.asList("Strength", "Dexterity", "Artifact", "Buffer", "Thorns",
                      "Intangible", "Metallicize");

    public Berreta92() {
        this(0);
    }

    public Berreta92(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, tooltips);
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
            this.customTooltips = Arrays.asList(toolTipInfo, toolTipInfo2);
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
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new VFXAction(new CustomSparksEffect(p.hb.cX, p.hb.cY)));
        for (int i = 0; i < 2; i++) {
            String x = powers.get(MathUtils.random(0, powers.size() - 1));
            switch (x) {
                default:
                case "Strength":
                    this.addToBot(
                        new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
                    break;
                case "Dexterity":
                    this.addToBot(
                        new ApplyPowerAction(p, p, new DexterityPower(p, 1)));
                    break;
                case "Artifact":
                    this.addToBot(
                        new ApplyPowerAction(p, p, new ArtifactPower(p, 1)));
                    break;
                case "Buffer":
                    this.addToBot(
                        new ApplyPowerAction(p, p, new BufferPower(p, 1)));
                    break;
                case "Thorns":
                    this.addToBot(
                        new ApplyPowerAction(p, p, new ThornsPower(p, 1)));
                    break;
                case "Intangible":
                    this.addToBot(new ApplyPowerAction(p, p,
                                                       new IntangiblePlayerPower(
                                                           p, 1)));
                    break;
                case "Metallicize":
                    this.addToBot(
                        new ApplyPowerAction(p, p, new MetallicizePower(p, 1)));
                    break;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Berreta92();
    }

    @Override
    public float getTitleFontSize() {
        return timesUpgraded >= MASTERY_LEVEL ? 20.0F : 27.0F;
    }
}
