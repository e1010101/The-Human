package theHuman.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDefaultCard extends CustomCard {

    public int defaultSecondMagicNumber;
    public int defaultBaseSecondMagicNumber;
    public boolean upgradedDefaultSecondMagicNumber;
    public boolean isDefaultSecondMagicNumberModified;
    protected List<AbstractCard> cardsToPreviewList = new ArrayList<>();
    private float rotationTimer = 0;
    private int previewIndex;

    public AbstractDefaultCard(final String id, final String name,
                               final String img, final int cost,
                               final String rawDescription, final CardType type,
                               final CardColor color, final CardRarity rarity,
                               final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) {
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
            isDefaultSecondMagicNumberModified = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if (!cardsToPreviewList.isEmpty()) {
            if (hb.hovered) {
                if (rotationTimer <= 0F) {
                    rotationTimer = getRotationTimeNeeded();
                    cardsToPreview = cardsToPreviewList.get(previewIndex);
                    if (previewIndex == cardsToPreviewList.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                } else {
                    rotationTimer -= Gdx.graphics.getDeltaTime();
                }
            }
        }
    }

    protected void upgradeCardToPreview() {
        for (AbstractCard q : cardsToPreviewList) {
            q.upgrade();
        }
    }

    protected float getRotationTimeNeeded() {
        return 2f;
    }

    public void upgradeDefaultSecondMagicNumber(int amount) {
        defaultBaseSecondMagicNumber += amount;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber;
        upgradedDefaultSecondMagicNumber = true;
    }
}