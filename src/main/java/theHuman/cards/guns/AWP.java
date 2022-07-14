package theHuman.cards.guns;

import basemod.helpers.TooltipInfo;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import theHuman.HumanMod;
import theHuman.actions.FireGunAction;
import theHuman.cards.AbstractShootWeaponCard;
import theHuman.cards.tokens.EmptyCartridge;
import theHuman.characters.TheHuman;
import theHuman.effects.CustomSmallLaserEffect;
import theHuman.powers.TargetedPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class AWP extends AbstractShootWeaponCard {

    public static final String ID = HumanMod.makeID(AWP.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION =
        cardStrings.UPGRADE_DESCRIPTION;
    private static final String MASTERED_NAME =
        cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG = makeCardPath("AWP.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    public static final String MASTERED_IMG =
        makeCardPath("AWP_Mastered" + ".png");
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":MasteryWords");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 40;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int UPGRADE_PLUS_SECOND_MAGIC = 3;
    private static final int MASTERY_LEVEL = 10;
    private static final String MASTERED_SMALL =
        getModID() + "Resources/images/cards/bonus/grunge.png";
    private static final String MASTERED_LARGE =
        getModID() + "Resources/images/cards/bonus/grunge_p.png";
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#c20000]" + uiStrings.TEXT[0],
                        "[#c20000]" + uiStrings.TEXT[1]);
    private static final List<TooltipInfo> tooltips =
        Collections.singletonList(toolTipInfo);

    public AWP() {
        this(0);
    }

    public AWP(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, tooltips);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 68;
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
        CardCrawlGame.sound.playAV("AWP", 1.0F, 3.5F);
        if (timesUpgraded >= MASTERY_LEVEL) {
            this.addToBot(new VFXAction(
                new CustomSmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY,
                                           Color.RED.cpy())));
        } else {
            this.addToBot(new VFXAction(
                new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY)));
        }
        this.addToBot(new FireGunAction(m, damage, defaultSecondMagicNumber,
                                        magicNumber));
        this.addToBot(
            new ApplyPowerAction(m, m, new TargetedPower(m, m, magicNumber)));
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
        boolean x = true;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDying && !m.isDead) {
                for (int i = 0; i < 2; i++) {
                    this.addToTop(new VFXAction(
                        new VfxBuilder(ImageMaster.CRYSTAL_IMPACT, p.drawX,
                                       p.hb.cY, 0.8f).scale(0.7F, 1.4f,
                                                            VfxBuilder.Interpolations.SWING)
                                                     .setColor(
                                                         x ? Color.RED.cpy() :
                                                         Color.WHITE.cpy())
                                                     .moveX(p.drawX, m.drawX,
                                                            VfxBuilder.Interpolations.EXP5IN)
                                                     .build()));
                    x = !x;
                }
            }
            for (AbstractPower pow : m.powers) {
                this.addToBot(new RemoveSpecificPowerAction(m, m, pow));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AWP();
    }
}
