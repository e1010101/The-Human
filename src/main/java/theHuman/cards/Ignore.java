package theHuman.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.patches.subscribers.OnShuffleSubscriber;
import theHuman.powers.PeacefulPower;
import theHuman.util.HumanUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static theHuman.HumanMod.getModID;
import static theHuman.HumanMod.makeCardPath;

public class Ignore extends AbstractDynamicCard implements OnShuffleSubscriber {

    public static final String ID =
        HumanMod.makeID(Ignore.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Ignore.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final UIStrings uiStrings =
        CardCrawlGame.languagePack.getUIString(getModID() + ":PacifistWords");
    private static final UIStrings uiStrings2 =
        CardCrawlGame.languagePack.getUIString(getModID() + ":LifestyleWords");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;

    private static final int BLOCK = 14;
    private static final int UPGRADE_PLUS_BLOCK = 6;
    private static final TooltipInfo toolTipInfo =
        new TooltipInfo("[#34eb52]" + uiStrings.TEXT[0], uiStrings.TEXT[1]);
    private static final TooltipInfo toolTipInfo2 =
        new TooltipInfo("[#2000a7]" + uiStrings2.TEXT[0], uiStrings2.TEXT[1]);
    private static final List<TooltipInfo> customTooltips =
        Arrays.asList(toolTipInfo, toolTipInfo2);

    public Ignore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, customTooltips);
        block = baseBlock = BLOCK;
        this.tags.add(HumanMod.HumanCardTags.PACIFIST_HUMAN);
    }

    public boolean shouldGlowSpecial() {
        return !HumanUtils.checkHandForAttacks(AbstractDungeon.player);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, block));

        if (!HumanUtils.checkHandForAttacks(p)) {
            this.addToBot(
                new ApplyPowerAction(p, p, new PeacefulPower(p, p, 12)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = shouldGlowSpecial() ? Color.FOREST.cpy() :
                         AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void onShuffle() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new PeacefulPower(p, p, 1)));
    }
}
