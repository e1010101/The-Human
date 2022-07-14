package theHuman.cards.guns;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
import theHuman.effects.CustomSmallLaserEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class Glock17 extends AbstractShootWeaponCard {

    public static final String ID =
        HumanMod.makeID(Glock17.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    private static final String MASTERED_NAME =
        cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG = makeCardPath("Glock17.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    // Credit to poceviciusgabrielius for image, https://3dexport.com/3dmodel-futuristic-pistol-341422.htm
    public static final String MASTERED_IMG =
        makeCardPath("Glock17_Mastered" + ".png");
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":MasteryWords");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 15;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int UPGRADE_PLUS_SECOND_MAGIC = 2;
    private static final int MASTERY_LEVEL = 8;
    private static final String MASTERED_SMALL =
        getModID() + "Resources/images/cards/bonus/hexagon.png";
    private static final String MASTERED_LARGE =
        getModID() + "Resources/images/cards/bonus/hexagon_p.png";
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#c20000]" + uiStrings.TEXT[0],
                        "[#c20000]" + uiStrings.TEXT[1]);
    private static final List<TooltipInfo> tooltips =
        Collections.singletonList(toolTipInfo);

    public Glock17() {
        this(0);
    }

    public Glock17(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, tooltips);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
        defaultSecondMagicNumber =
        defaultBaseSecondMagicNumber = SECOND_MAGIC_NUMBER;
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
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractPlayer p = AbstractDungeon.player;
            for (int i = 0; i < 3; i++) {
                AbstractMonster mo =
                    AbstractDungeon.getCurrRoom().monsters.getRandomMonster(
                        true);
                this.addToBot(new VFXAction(
                    new CustomSmallLaserEffect(mo.hb.cX, mo.hb.cY, p.hb.cX,
                                               p.hb.cY, Color.VIOLET.cpy())));
                this.addToBot(new FireGunAction(mo, 10, 100, 1,
                                                DamageInfo.DamageType.HP_LOSS));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Glock17();
    }
}
