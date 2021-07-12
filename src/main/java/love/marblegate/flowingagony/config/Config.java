package love.marblegate.flowingagony.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec ACT_CONFIG;
    public static final ForgeConfigSpec.BooleanValue HYBRID_SERVER_USER;

    public static final ForgeConfigSpec.BooleanValue VILLAGER_SAFE_MODE;

    public static final ForgeConfigSpec.BooleanValue AN_ENCHANTED_GOLDEN_APPLE_A_DAY;
    public static final ForgeConfigSpec.BooleanValue DEATH_PUNK;
    public static final ForgeConfigSpec.BooleanValue EXOTIC_HEALER;
    public static final ForgeConfigSpec.BooleanValue SAVOR_THE_TASTED;
    public static final ForgeConfigSpec.BooleanValue TRICKSTER;
    public static final ForgeConfigSpec.BooleanValue COVERT_KNIFE;
    public static final ForgeConfigSpec.BooleanValue ENCIOUS_KIND;
    public static final ForgeConfigSpec.BooleanValue EYESORE;
    public static final ForgeConfigSpec.BooleanValue SOURCE_OF_ENVY;
    public static final ForgeConfigSpec.BooleanValue THORN_IN_FLESH;
    public static final ForgeConfigSpec.BooleanValue CLEANSING_BEFORE_USING;
    public static final ForgeConfigSpec.BooleanValue COME_BACK_AT_DUSK;
    public static final ForgeConfigSpec.BooleanValue DIRTY_MONEY;
    public static final ForgeConfigSpec.BooleanValue PILFERAGE_CREED;
    public static final ForgeConfigSpec.BooleanValue REGULAR_CUSTOMER_PROGRAM;
    public static final ForgeConfigSpec.BooleanValue ARMOR_UP;
    public static final ForgeConfigSpec.BooleanValue FRIVOLOUS_STEP;
    public static final ForgeConfigSpec.BooleanValue MIRACULOUS_ESCAPE;
    public static final ForgeConfigSpec.BooleanValue POTENTIAL_BURST;
    public static final ForgeConfigSpec.BooleanValue STUBBORN_STEP;
    public static final ForgeConfigSpec.BooleanValue INFECTIOUS_MALICE;
    public static final ForgeConfigSpec.BooleanValue I_SEE_YOU_NOW;
    public static final ForgeConfigSpec.BooleanValue MALICE_OUTBREAK;
    public static final ForgeConfigSpec.BooleanValue PERCEIVED_MALICE;
    public static final ForgeConfigSpec.BooleanValue VENGEANCE;
    public static final ForgeConfigSpec.BooleanValue AGONY_SCREAMER;
    public static final ForgeConfigSpec.BooleanValue CUTTING_WATERMELON_DREAM;
    public static final ForgeConfigSpec.BooleanValue INSANE_POET;
    public static final ForgeConfigSpec.BooleanValue PAPER_BRAIN;
    public static final ForgeConfigSpec.BooleanValue SHOCK_THERAPY;
    public static final ForgeConfigSpec.BooleanValue BURNING_PHOBIA;
    public static final ForgeConfigSpec.BooleanValue CONSTRAINED_HEART;
    public static final ForgeConfigSpec.BooleanValue DROWNING_PHOBIA;
    public static final ForgeConfigSpec.BooleanValue PIERCING_FEVER;
    public static final ForgeConfigSpec.BooleanValue PRAYER_OF_PAIN;
    public static final ForgeConfigSpec.BooleanValue FRESH_REVENGE;
    public static final ForgeConfigSpec.BooleanValue HATRED_BLOODLINE;
    public static final ForgeConfigSpec.BooleanValue OUTRAGEOUS_SPIRIT;
    public static final ForgeConfigSpec.BooleanValue RESENTFUL_SOUL;
    public static final ForgeConfigSpec.BooleanValue TOO_RESENTFUL_TO_DIE;
    public static final ForgeConfigSpec.BooleanValue MORIRS_DEATHWISH;
    public static final ForgeConfigSpec.BooleanValue MORIRS_LIFEBOUND;
    public static final ForgeConfigSpec.BooleanValue NECESSARY_EVIL;
    public static final ForgeConfigSpec.BooleanValue SURVIVAL_RUSE;
    public static final ForgeConfigSpec.BooleanValue SURVIVAL_SHORTCUT;
    public static final ForgeConfigSpec.BooleanValue CORRUPTED_KINDRED;
    public static final ForgeConfigSpec.BooleanValue LIGHTBURN_FUNGAL_PARASITIC;
    public static final ForgeConfigSpec.BooleanValue PROTOTYPE_CHAOTIC;
    public static final ForgeConfigSpec.BooleanValue PROTOTYPE_CHAOTIC_TYPE_BETA;
    public static final ForgeConfigSpec.BooleanValue SHADOWBORN;
    public static final ForgeConfigSpec.BooleanValue BURIAL_OBJECT;
    public static final ForgeConfigSpec.BooleanValue BACK_AND_FILL;
    public static final ForgeConfigSpec.BooleanValue SCHOLAR_OF_ORIGINAL_SIN;
    public static final ForgeConfigSpec.BooleanValue ORIGINAL_SIN_EROSION;
    public static final ForgeConfigSpec.BooleanValue GUIDENS_REGRET;
    public static final ForgeConfigSpec.BooleanValue LAST_SWEET_DREAM;
    public static final ForgeConfigSpec.BooleanValue NIMBLE_FINGER;
    public static final ForgeConfigSpec.BooleanValue CAREFULLY_IDENTIFIED;
    public static final ForgeConfigSpec.BooleanValue DESTRUCTION_WORSHIP;


    static{
        ForgeConfigSpec.Builder ACT_BUILDER = new ForgeConfigSpec.Builder();
        ACT_BUILDER.push("general");
        VILLAGER_SAFE_MODE = ACT_BUILDER.comment(
                "If it's set to \"true\", \"Lightburn Fungal Parasitic\" cannot spread negative effect to villager,",
                "and \"Survival Shortcut\", \"Necessary Evil\", \"Pilferage Creed\" cannot damage villager.").define("VILLAGER_SAFE_MODE",false);
        ACT_BUILDER.pop();

        ACT_BUILDER.push("compatibility");
        HYBRID_SERVER_USER = ACT_BUILDER.comment(
                "-------------IMPORTANT!-------------",
                "If you are using hybrid server and encountered problem with anvil, please set this config option to \"true\".",
                "NOTICE: If you are using ARCLIGHT server version below 1.0.19, you must set this to \"true\"",
                "Enchantment \"Nimble Finger\" will be complete disabled,",
                "which means it cannot be obtained and applied on survival mode and it won't function.",
                "It is because hybrid server are changing forge's event hook and it does cause serious bugs sometimes.",
                "We are not responsible to fixing bug caused hybrid server, but here we still offer a temporary solution.").define("HYBRID_SERVER_USER",false);
        ACT_BUILDER.pop();

        ACT_BUILDER.comment("Enchantment Acquirable Setting",
                "If you set certain item to \"false\", the relevant enchantment will become not acquirable to survival mode player.",
                "Enchantment which is acquirable is still functional.").push("acquirable");
        AN_ENCHANTED_GOLDEN_APPLE_A_DAY = ACT_BUILDER.define("AN_ENCHANTED_GOLDEN_APPLE_A_DAY",true);
        DEATH_PUNK = ACT_BUILDER.define("DEATH_PUNK",true);
        EXOTIC_HEALER = ACT_BUILDER.define("EXOTIC_HEALER",true);
        SAVOR_THE_TASTED = ACT_BUILDER.define("SAVOR_THE_TASTED",true);
        TRICKSTER = ACT_BUILDER.define("TRICKSTER",true);
        COVERT_KNIFE = ACT_BUILDER.define("COVERT_KNIFE",true);
        ENCIOUS_KIND = ACT_BUILDER.define("ENCIOUS_KIND",true);
        EYESORE = ACT_BUILDER.define("EYESORE",true);
        SOURCE_OF_ENVY = ACT_BUILDER.define("SOURCE_OF_ENVY",true);
        THORN_IN_FLESH = ACT_BUILDER.define("THORN_IN_FLESH",true);
        CLEANSING_BEFORE_USING = ACT_BUILDER.define("CLEANSING_BEFORE_USING",true);
        COME_BACK_AT_DUSK = ACT_BUILDER.define("COME_BACK_AT_DUSK",true);
        DIRTY_MONEY = ACT_BUILDER.define("DIRTY_MONEY",true);
        PILFERAGE_CREED = ACT_BUILDER.define("PILFERAGE_CREED",true);
        CAREFULLY_IDENTIFIED = ACT_BUILDER.define("CAREFULLY_IDENTIFIED",true);
        NIMBLE_FINGER = ACT_BUILDER.define("NIMBLE_FINGER",true);
        REGULAR_CUSTOMER_PROGRAM = ACT_BUILDER.define("REGULAR_CUSTOMER_PROGRAM",true);
        ARMOR_UP = ACT_BUILDER.define("ARMOR_UP",true);
        FRIVOLOUS_STEP = ACT_BUILDER.define("FRIVOLOUS_STEP",true);
        MIRACULOUS_ESCAPE = ACT_BUILDER.define("MIRACULOUS_ESCAPE",true);
        POTENTIAL_BURST = ACT_BUILDER.define("POTENTIAL_BURST",true);
        STUBBORN_STEP = ACT_BUILDER.define("STUBBORN_STEP",true);
        INFECTIOUS_MALICE = ACT_BUILDER.define("INFECTIOUS_MALICE",true);
        I_SEE_YOU_NOW = ACT_BUILDER.define("I_SEE_YOU_NOW",true);
        MALICE_OUTBREAK = ACT_BUILDER.define("MALICE_OUTBREAK",true);
        PERCEIVED_MALICE = ACT_BUILDER.define("PERCEIVED_MALICE",true);
        VENGEANCE = ACT_BUILDER.define("VENGEANCE",true);
        BACK_AND_FILL = ACT_BUILDER.define("BACK_AND_FILL",true);
        AGONY_SCREAMER = ACT_BUILDER.define("AGONY_SCREAMER",true);
        CUTTING_WATERMELON_DREAM = ACT_BUILDER.define("CUTTING_WATERMELON_DREAM",true);
        INSANE_POET = ACT_BUILDER.define("INSANE_POET",true);
        PAPER_BRAIN = ACT_BUILDER.define("PAPER_BRAIN",true);
        SHOCK_THERAPY = ACT_BUILDER.define("SHOCK_THERAPY",true);
        BURNING_PHOBIA = ACT_BUILDER.define("BURNING_PHOBIA",true);
        CONSTRAINED_HEART = ACT_BUILDER.define("CONSTRAINED_HEART",true);
        DROWNING_PHOBIA = ACT_BUILDER.define("DROWNING_PHOBIA",true);
        PIERCING_FEVER = ACT_BUILDER.define("PIERCING_FEVER",true);
        DESTRUCTION_WORSHIP = ACT_BUILDER.define("DESTRUCTION_WORSHIP",true);
        PRAYER_OF_PAIN = ACT_BUILDER.define("PRAYER_OF_PAIN",true);
        FRESH_REVENGE = ACT_BUILDER.define("FRESH_REVENGE",true);
        HATRED_BLOODLINE = ACT_BUILDER.define("HATRED_BLOODLINE",true);
        OUTRAGEOUS_SPIRIT = ACT_BUILDER.define("OUTRAGEOUS_SPIRIT",true);
        RESENTFUL_SOUL = ACT_BUILDER.define("RESENTFUL_SOUL",true);
        TOO_RESENTFUL_TO_DIE = ACT_BUILDER.define("TOO_RESENTFUL_TO_DIE",true);
        MORIRS_DEATHWISH = ACT_BUILDER.define("MORIRS_DEATHWISH",true);
        MORIRS_LIFEBOUND = ACT_BUILDER.define("MORIRS_LIFEBOUND",true);
        GUIDENS_REGRET = ACT_BUILDER.define("GUIDENS_REGRET",true);
        LAST_SWEET_DREAM = ACT_BUILDER.define("LAST_SWEET_DREAM",true);
        NECESSARY_EVIL = ACT_BUILDER.define("NECESSARY_EVIL",true);
        SURVIVAL_RUSE = ACT_BUILDER.define("SURVIVAL_RUSE",true);
        SURVIVAL_SHORTCUT = ACT_BUILDER.define("SURVIVAL_SHORTCUT",true);
        CORRUPTED_KINDRED = ACT_BUILDER.define("CORRUPTED_KINDRED",true);
        LIGHTBURN_FUNGAL_PARASITIC = ACT_BUILDER.define("LIGHTBURN_FUNGAL_PARASITIC",true);
        PROTOTYPE_CHAOTIC = ACT_BUILDER.define("PROTOTYPE_CHAOTIC",true);
        PROTOTYPE_CHAOTIC_TYPE_BETA = ACT_BUILDER.define("PROTOTYPE_CHAOTIC_TYPE_BETA",true);
        SHADOWBORN = ACT_BUILDER.define("SHADOWBORN",true);
        SCHOLAR_OF_ORIGINAL_SIN = ACT_BUILDER.define("SCHOLAR_OF_ORIGINAL_SIN",true);
        ORIGINAL_SIN_EROSION = ACT_BUILDER.define("ORIGINAL_SIN_EROSION",true);
        BURIAL_OBJECT = ACT_BUILDER.define("BURIAL_OBJECT",true);
        ACT_BUILDER.pop();
        ACT_CONFIG = ACT_BUILDER.build();
    }
}
