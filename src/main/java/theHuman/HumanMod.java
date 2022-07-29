package theHuman;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theHuman.cards.AbstractDefaultCard;
import theHuman.characters.TheHuman;
import theHuman.relics.HumanRelic;
import theHuman.util.TextureLoader;
import theHuman.variables.DefaultCustomVariable;
import theHuman.variables.DefaultSecondMagicNumber;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class HumanMod
    implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber,
               EditKeywordsSubscriber, EditCharactersSubscriber,
               PostInitializeSubscriber {
    public static final Logger logger =
        LogManager.getLogger(HumanMod.class.getName());
    public static final String ENABLE_PLACEHOLDER_SETTINGS =
        "enablePlaceholder";

    public static final Color HUMAN_SKIN = CardHelper.getColor(216, 178, 123);
    public static final String THE_DEFAULT_SHOULDER_1 =
        "theHumanResources/images/char/humanCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 =
        "theHumanResources/images/char/humanCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE =
        "theHumanResources/images/char/humanCharacter/corpse.png";
    public static final String BADGE_IMAGE =
        "theHumanResources/images/Badge.png";
    public static final String ATTACK_DEFAULT_SKIN =
        "theHumanResources/images/512/bg_attack_default_skin.png";
    public static final String SKILL_DEFAULT_SKIN =
        "theHumanResources/images/512/bg_skill_default_skin.png";
    public static final String POWER_DEFAULT_SKIN =
        "theHumanResources/images/512/bg_power_default_skin.png";
    public static final String ATTACK_DEFAULT_SKIN_PORTRAIT =
        "theHumanResources/images/1024/bg_attack_default_skin.png";
    public static final String SKILL_DEFAULT_SKIN_PORTRAIT =
        "theHumanResources/images/1024/bg_skill_default_skin.png";
    public static final String POWER_DEFAULT_SKIN_PORTRAIT =
        "theHumanResources/images/1024/bg_power_default_skin.png";
    private static final String MODNAME = "The Human Mod";
    private static final String AUTHOR = "Complex";
    private static final String DESCRIPTION =
        "Adds Harold the Human to the game.";
    private static final String ENERGY_ORB_DEFAULT_SKIN =
        "theHumanResources/images/512/card_human_orb_small.png";
    private static final String CARD_ENERGY_ORB =
        "theHumanResources/images/512/card_small_orb.png";
    private static final String ENERGY_ORB_DEFAULT_SKIN_PORTRAIT =
        "theHumanResources/images/1024/card_human_orb.png";
    private static final String THE_HUMAN_BUTTON =
        "theHumanResources/images/charSelect/humanCharacterButton.png";
    private static final String THE_HUMAN_PORTRAIT =
        "theHumanResources/images/charSelect/humanCharacterPortraitBG.png";

    public static Properties theHumanSettings = new Properties();
    public static boolean enablePlaceholder = true;
    private static String modID;

    public HumanMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("theHuman");
        logger.info("Done subscribing");
        BaseMod.addColor(TheHuman.Enums.COLOR_SKIN, HUMAN_SKIN, HUMAN_SKIN,
                         HUMAN_SKIN, HUMAN_SKIN, HUMAN_SKIN, HUMAN_SKIN,
                         HUMAN_SKIN, ATTACK_DEFAULT_SKIN, SKILL_DEFAULT_SKIN,
                         POWER_DEFAULT_SKIN, ENERGY_ORB_DEFAULT_SKIN,
                         ATTACK_DEFAULT_SKIN_PORTRAIT,
                         SKILL_DEFAULT_SKIN_PORTRAIT,
                         POWER_DEFAULT_SKIN_PORTRAIT,
                         ENERGY_ORB_DEFAULT_SKIN_PORTRAIT, CARD_ENERGY_ORB);
        logger.info("Adding mod settings");
        theHumanSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config =
                new SpireConfig("humanMod", "theHumanConfig", theHumanSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeScenePath(String resourcePath) {
        return getModID() + "Resources/images/scenes/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void setModID(String ID) {
        modID = ID;
        logger.info("Success! ID is " + modID);
    }

    public static void initialize() {
        logger.info(
            "========================= Initializing Human Mod. Hi. =========================");
        HumanMod humanMod = new HumanMod();
        logger.info(
            "========================= /Human Mod Initialized. Good day./ =========================");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " +
                    TheHuman.Enums.THE_HUMAN.toString());
        BaseMod.addCharacter(
            new TheHuman("the Human", TheHuman.Enums.THE_HUMAN),
            THE_HUMAN_BUTTON, THE_HUMAN_PORTRAIT, TheHuman.Enums.THE_HUMAN);

        receiveEditPotions();
        logger.info("Added " + TheHuman.Enums.THE_HUMAN.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton =
            new ModLabeledToggleButton("Test Configs.", 350.0f, 700.0f,
                                       Settings.CREAM_COLOR,
                                       FontHelper.charDescFont,
                                       enablePlaceholder, settingsPanel,
                                       (label) -> {
                                       }, (button) -> {

                enablePlaceholder = button.enabled;
                try {
                    SpireConfig config =
                        new SpireConfig("humanMod", "theHumanConfig",
                                        theHumanSettings);
                    config.setBool(ENABLE_PLACEHOLDER_SETTINGS,
                                   enablePlaceholder);
                    config.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        settingsPanel.addUIElement(enableNormalsButton);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION,
                                 settingsPanel);
        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditPotions() {
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        new AutoAdd(modID).packageFilter(HumanRelic.class)
                          .any(CustomRelic.class, (info, relic) -> {
                              BaseMod.addRelicToCustomPool(relic,
                                                           TheHuman.Enums.COLOR_SKIN);
                              if (!info.seen) {
                                  UnlockTracker.markRelicAsSeen(relic.relicId);
                              }
                          });
        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        logger.info("Add variables");
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        logger.info("Adding cards");
        new AutoAdd(getModID()).packageFilter(AbstractDefaultCard.class)
                               .setDefaultSeen(true).cards();
        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        BaseMod.loadCustomStringsFile(CardStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Card-Strings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Power-Strings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Relic-Strings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Event-Strings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Potion-Strings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Character-Strings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-Orb-Strings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                                      getModID() + "Resources/localization/" +
                                      loadLocalizationIfAvailable(
                                          "/HumanMod-UI-Strings.json"));
        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(
                             getModID() + "Resources/localization/" +
                             loadLocalizationIfAvailable("/HumanMod-Keyword-Strings.json"))
                               .readString(
                                   String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords =
            gson.fromJson(json,
                          com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(),
                                   keyword.PROPER_NAME, keyword.NAMES,
                                   keyword.DESCRIPTION);
            }
        }
    }

    private String loadLocalizationIfAvailable(String fileName) {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" +
                                Settings.language.toString().toLowerCase() +
                                "/" + fileName).exists()) {
            logger.info(
                "Language: " + Settings.language.toString().toLowerCase() +
                ", not currently supported for " + fileName + ".");
            return "eng" + "/" + fileName;
        } else {
            logger.info("Loaded Language: " +
                        Settings.language.toString().toLowerCase() + ", for " +
                        fileName + ".");
            return Settings.language.toString().toLowerCase() + "/" + fileName;
        }
    }

    public static class HumanCardTags {
        @SpireEnum(name = "SLAP_HUMAN") public static AbstractCard.CardTags
            SLAP_HUMAN;

        @SpireEnum(name = "JUNK_HUMAN") public static AbstractCard.CardTags
            JUNK_HUMAN;

        @SpireEnum(name = "WEAPON_HUMAN") public static AbstractCard.CardTags
            WEAPON_HUMAN;

        @SpireEnum(name = "SHOOTER_HUMAN") public static AbstractCard.CardTags
            SHOOTER_HUMAN;

        @SpireEnum(name = "TECHNIQUE_HUMAN") public static AbstractCard.CardTags
            TECHNIQUE_HUMAN;

        @SpireEnum(name = "PACIFIST_HUMAN") public static AbstractCard.CardTags
            PACIFIST_HUMAN;
    }
}
