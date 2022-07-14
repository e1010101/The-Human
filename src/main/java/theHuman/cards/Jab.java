package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.TechniquePower;

import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class Jab extends AbstractDynamicCard {

    public static final String ID = HumanMod.makeID(Jab.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Jab.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":TechniqueWords");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;

    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#a83277]" + uiStrings.TEXT[0], uiStrings.TEXT[1]);
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public Jab() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
        this.tags.add(HumanMod.HumanCardTags.TECHNIQUE_HUMAN);
    }

    public boolean shouldGlowSpecial() {
        return AbstractDungeon.player.hasPower(TechniquePower.POWER_ID);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.exhaust = false;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                             AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new DrawCardAction(magicNumber));
        if (p.hasPower(TechniquePower.POWER_ID)) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,
                                                             damageTypeForTurn),
                                           AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        } else {
            this.addToBot(
                new ApplyPowerAction(p, p, new TechniquePower(p, p, 1)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = shouldGlowSpecial() ? Color.valueOf("a83277") :
                         AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}
