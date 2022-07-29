package theHuman.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theHuman.HumanMod;
import theHuman.cards.Defend;
import theHuman.cards.Focused;
import theHuman.cards.Preparation;
import theHuman.cards.Slap;
import theHuman.relics.HumanRelic;

import java.util.ArrayList;
import java.util.List;

import static theHuman.HumanMod.*;
import static theHuman.characters.TheHuman.Enums.COLOR_SKIN;

public class TheHuman extends CustomPlayer {
    public static final Logger logger =
        LogManager.getLogger(HumanMod.class.getName());

    public static final int ENERGY_PER_TURN = 3;

    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    public static final String[] orbTextures =
        {"theHumanResources/images/char/humanCharacter/orb/layer1.png",
         "theHumanResources/images/char/humanCharacter/orb/layer2.png",
         "theHumanResources/images/char/humanCharacter/orb/layer3.png",
         "theHumanResources/images/char/humanCharacter/orb/layer4.png",
         "theHumanResources/images/char/humanCharacter/orb/layer5.png",
         "theHumanResources/images/char/humanCharacter/orb/layer6.png",
         "theHumanResources/images/char/humanCharacter/orb/layer1d.png",
         "theHumanResources/images/char/humanCharacter/orb/layer2d.png",
         "theHumanResources/images/char/humanCharacter/orb/layer3d.png",
         "theHumanResources/images/char/humanCharacter/orb/layer4d.png",
         "theHumanResources/images/char/humanCharacter/orb/layer5d.png",};

    private static final String ID = makeID("HumanCharacter");
    private static final CharacterStrings characterStrings =
        CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public TheHuman(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
              getModID() + "Resources/images/char/humanCharacter/orb/vfx.png",
              null, new SpriterAnimation(getModID() + "Resources/images/char" +
                                         "/humanCharacter/Human.scml"));

        initializeClass(null, THE_DEFAULT_SHOULDER_2, THE_DEFAULT_SHOULDER_1,
                        THE_DEFAULT_CORPSE, getLoadout(), 20.0F, -10.0F, 166.0F,
                        327.0F, new EnergyManager(ENERGY_PER_TURN));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            retVal.add(Slap.ID);
        }
        for (int i = 0; i < 5; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(Preparation.ID);
        retVal.add(Focused.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HumanRelic.ID);
        UnlockTracker.markRelicAsSeen(HumanRelic.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0], STARTING_HP, MAX_HP,
                                  ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                                  getStartingRelics(), getStartingDeck(),
                                  false);
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_SKIN;
    }

    @Override
    public Color getCardRenderColor() {
        return HUMAN_SKIN;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Slap();
    }

    @Override
    public Color getCardTrailColor() {
        return HUMAN_SKIN;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW,
                                        ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheHuman(name, chosenClass);
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return HUMAN_SKIN;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
            AbstractGameAction.AttackEffect.BLUNT_HEAVY,
            AbstractGameAction.AttackEffect.SMASH,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage(makeScenePath("background.png"));
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(makeScenePath("background1.png"),
                                     "ATTACK_HEAVY"));
        panels.add(new CutscenePanel(makeScenePath("background2.png")));
        panels.add(new CutscenePanel(makeScenePath("background3.png")));
        return panels;
    }

    public static class Enums {
        @SpireEnum public static AbstractPlayer.PlayerClass THE_HUMAN;
        @SpireEnum(name = "HUMAN_COLOR") public static AbstractCard.CardColor
            COLOR_SKIN;
        @SpireEnum(name = "HUMAN_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR_SKIN;
    }

}
