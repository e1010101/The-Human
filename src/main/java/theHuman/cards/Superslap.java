package theHuman.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

public class Superslap extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(Superslap.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("Superslap.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    public Superslap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 5;
        this.tags.add(HumanMod.HumanCardTags.SLAP_HUMAN);
        cardsToPreview = new Slap();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {

            AbstractMonster targetMonster =
                AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);

            AbstractCard tmp = new Slap();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x =
            tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            tmp.current_y = tmp.target_y = Settings.HEIGHT / 2.0f;
            if (targetMonster != null) {
                tmp.calculateCardDamage(targetMonster);
            }
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(
                new CardQueueItem(tmp, targetMonster, 0, false, true));
        }
    }
}
