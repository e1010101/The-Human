package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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

public class CrescentKick extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(CrescentKick.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("CrescentKick.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":TechniqueWords");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#a83277]" + uiStrings.TEXT[0], uiStrings.TEXT[1]);
    private static final List<TooltipInfo> customTooltips =
        Collections.singletonList(toolTipInfo);

    public CrescentKick() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        damage = baseDamage = DAMAGE;
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
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK) {
                count++;
            }
        }

        if (p.hasPower(TechniquePower.POWER_ID)) {
            while (count > 0) {
                this.addToBot(
                    new DamageAllEnemiesAction(p, damage, damageTypeForTurn,
                                               AbstractGameAction.AttackEffect.SMASH));
                count--;
            }
        } else {
            while (count > 0) {
                this.addToBot(new DamageAction(m, new DamageInfo(p, damage,
                                                                 damageTypeForTurn),
                                               AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                count--;
            }

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
