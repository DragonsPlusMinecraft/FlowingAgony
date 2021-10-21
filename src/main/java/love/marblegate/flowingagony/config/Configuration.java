package love.marblegate.flowingagony.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    public static final ForgeConfigSpec ACT_CONFIG;

    public static class GeneralSetting {
        public static ForgeConfigSpec.BooleanValue VILLAGER_SAFE_MODE;
        public static ForgeConfigSpec.DoubleValue INSANE_POET_DAMAGE_REDUCTION;
        public static ForgeConfigSpec.DoubleValue SHOCK_THERAPY_DAMAGE_REDUCTION;
        public static ForgeConfigSpec.DoubleValue PAPER_BRAIN_DAMAGE_REDUCTION;
    }

    public static class AcquirableSetting {
        public static ForgeConfigSpec.BooleanValue AN_ENCHANTED_GOLDEN_APPLE_A_DAY;
        public static ForgeConfigSpec.BooleanValue DEATH_PUNK;
        public static ForgeConfigSpec.BooleanValue EXOTIC_HEALER;
        public static ForgeConfigSpec.BooleanValue SAVOR_THE_TASTED;
        public static ForgeConfigSpec.BooleanValue TRICKSTER;
        public static ForgeConfigSpec.BooleanValue COVERT_KNIFE;
        public static ForgeConfigSpec.BooleanValue ENCIOUS_KIND;
        public static ForgeConfigSpec.BooleanValue EYESORE;
        public static ForgeConfigSpec.BooleanValue SOURCE_OF_ENVY;
        public static ForgeConfigSpec.BooleanValue THORN_IN_FLESH;
        public static ForgeConfigSpec.BooleanValue CLEANSING_BEFORE_USING;
        public static ForgeConfigSpec.BooleanValue COME_BACK_AT_DUSK;
        public static ForgeConfigSpec.BooleanValue DIRTY_MONEY;
        public static ForgeConfigSpec.BooleanValue PILFERAGE_CREED;
        public static ForgeConfigSpec.BooleanValue REGULAR_CUSTOMER_PROGRAM;
        public static ForgeConfigSpec.BooleanValue ARMOR_UP;
        public static ForgeConfigSpec.BooleanValue FRIVOLOUS_STEP;
        public static ForgeConfigSpec.BooleanValue MIRACULOUS_ESCAPE;
        public static ForgeConfigSpec.BooleanValue POTENTIAL_BURST;
        public static ForgeConfigSpec.BooleanValue STUBBORN_STEP;
        public static ForgeConfigSpec.BooleanValue INFECTIOUS_MALICE;
        public static ForgeConfigSpec.BooleanValue I_SEE_YOU_NOW;
        public static ForgeConfigSpec.BooleanValue MALICE_OUTBREAK;
        public static ForgeConfigSpec.BooleanValue PERCEIVED_MALICE;
        public static ForgeConfigSpec.BooleanValue VENGEANCE;
        public static ForgeConfigSpec.BooleanValue AGONY_SCREAMER;
        public static ForgeConfigSpec.BooleanValue CUTTING_WATERMELON_DREAM;
        public static ForgeConfigSpec.BooleanValue INSANE_POET;
        public static ForgeConfigSpec.BooleanValue PAPER_BRAIN;
        public static ForgeConfigSpec.BooleanValue SHOCK_THERAPY;
        public static ForgeConfigSpec.BooleanValue BURNING_PHOBIA;
        public static ForgeConfigSpec.BooleanValue CONSTRAINED_HEART;
        public static ForgeConfigSpec.BooleanValue DROWNING_PHOBIA;
        public static ForgeConfigSpec.BooleanValue PIERCING_FEVER;
        public static ForgeConfigSpec.BooleanValue PRAYER_OF_PAIN;
        public static ForgeConfigSpec.BooleanValue FRESH_REVENGE;
        public static ForgeConfigSpec.BooleanValue HATRED_BLOODLINE;
        public static ForgeConfigSpec.BooleanValue OUTRAGEOUS_SPIRIT;
        public static ForgeConfigSpec.BooleanValue RESENTFUL_SOUL;
        public static ForgeConfigSpec.BooleanValue TOO_RESENTFUL_TO_DIE;
        public static ForgeConfigSpec.BooleanValue MORIRS_DEATHWISH;
        public static ForgeConfigSpec.BooleanValue MORIRS_LIFEBOUND;
        public static ForgeConfigSpec.BooleanValue NECESSARY_EVIL;
        public static ForgeConfigSpec.BooleanValue SURVIVAL_RUSE;
        public static ForgeConfigSpec.BooleanValue SURVIVAL_SHORTCUT;
        public static ForgeConfigSpec.BooleanValue CORRUPTED_KINDRED;
        public static ForgeConfigSpec.BooleanValue LIGHTBURN_FUNGAL_PARASITIC;
        public static ForgeConfigSpec.BooleanValue PROTOTYPE_CHAOTIC;
        public static ForgeConfigSpec.BooleanValue PROTOTYPE_CHAOTIC_TYPE_BETA;
        public static ForgeConfigSpec.BooleanValue SHADOWBORN;
        public static ForgeConfigSpec.BooleanValue BURIAL_OBJECT;
        public static ForgeConfigSpec.BooleanValue BACK_AND_FILL;
        public static ForgeConfigSpec.BooleanValue SCHOLAR_OF_ORIGINAL_SIN;
        public static ForgeConfigSpec.BooleanValue ORIGINAL_SIN_EROSION;
        public static ForgeConfigSpec.BooleanValue GUIDENS_REGRET;
        public static ForgeConfigSpec.BooleanValue LAST_SWEET_DREAM;
        public static ForgeConfigSpec.BooleanValue NIMBLE_FINGER;
        public static ForgeConfigSpec.BooleanValue CAREFULLY_IDENTIFIED;
        public static ForgeConfigSpec.BooleanValue DESTRUCTION_WORSHIP;
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("general");
        GeneralSetting.VILLAGER_SAFE_MODE = builder.comment(
                        "If it's set to \"true\", \"Lightburn Fungal Parasitic\" cannot spread negative effect to villager,",
                        "and \"Survival Shortcut\", \"Necessary Evil\", \"Pilferage Creed\" cannot damage villager.")
                .define("VILLAGER_SAFE_MODE", false);
        GeneralSetting.INSANE_POET_DAMAGE_REDUCTION = builder.comment(
                        "The attack damage reduction ratio of \"Insane Poet\". Default value is 0.9, which means the attack damage will be decreased by 90%.")
                .defineInRange("INSANE_POET_DAMAGE_REDUCTION", 0.9, 0, 1);
        GeneralSetting.SHOCK_THERAPY_DAMAGE_REDUCTION = builder.comment(
                        "The attack damage reduction ratio of \"Shock Therapy\". Default value is 0.9, which means the attack damage will be decreased by 90%.")
                .defineInRange("SHOCK_THERAPY_DAMAGE_REDUCTION", 0.9, 0, 1);
        GeneralSetting.PAPER_BRAIN_DAMAGE_REDUCTION = builder.comment(
                        "The attack damage reduction ratio of \"Paper Brain\". Default value is 0.9, which means the attack damage will be decreased by 90%.")
                .defineInRange("PAPER_BRAIN_DAMAGE_REDUCTION", 0.9, 0, 1);
        builder.pop();

        builder.comment("Enchantment Acquirable Setting",
                "If you set certain item to \"false\", the relevant enchantment will become not acquirable to survival mode player.",
                "Enchantment which is acquirable is still functional.").push("acquirable");
        AcquirableSetting.AN_ENCHANTED_GOLDEN_APPLE_A_DAY = builder.define("AN_ENCHANTED_GOLDEN_APPLE_A_DAY", true);
        AcquirableSetting.DEATH_PUNK = builder.define("DEATH_PUNK", true);
        AcquirableSetting.EXOTIC_HEALER = builder.define("EXOTIC_HEALER", true);
        AcquirableSetting.SAVOR_THE_TASTED = builder.define("SAVOR_THE_TASTED", true);
        AcquirableSetting.TRICKSTER = builder.define("TRICKSTER", true);
        AcquirableSetting.COVERT_KNIFE = builder.define("COVERT_KNIFE", true);
        AcquirableSetting.ENCIOUS_KIND = builder.define("ENCIOUS_KIND", true);
        AcquirableSetting.EYESORE = builder.define("EYESORE", true);
        AcquirableSetting.SOURCE_OF_ENVY = builder.define("SOURCE_OF_ENVY", true);
        AcquirableSetting.THORN_IN_FLESH = builder.define("THORN_IN_FLESH", true);
        AcquirableSetting.CLEANSING_BEFORE_USING = builder.define("CLEANSING_BEFORE_USING", true);
        AcquirableSetting.COME_BACK_AT_DUSK = builder.define("COME_BACK_AT_DUSK", true);
        AcquirableSetting.DIRTY_MONEY = builder.define("DIRTY_MONEY", true);
        AcquirableSetting.PILFERAGE_CREED = builder.define("PILFERAGE_CREED", true);
        AcquirableSetting.CAREFULLY_IDENTIFIED = builder.define("CAREFULLY_IDENTIFIED", true);
        AcquirableSetting.NIMBLE_FINGER = builder.define("NIMBLE_FINGER", true);
        AcquirableSetting.REGULAR_CUSTOMER_PROGRAM = builder.define("REGULAR_CUSTOMER_PROGRAM", true);
        AcquirableSetting.ARMOR_UP = builder.define("ARMOR_UP", true);
        AcquirableSetting.FRIVOLOUS_STEP = builder.define("FRIVOLOUS_STEP", true);
        AcquirableSetting.MIRACULOUS_ESCAPE = builder.define("MIRACULOUS_ESCAPE", true);
        AcquirableSetting.POTENTIAL_BURST = builder.define("POTENTIAL_BURST", true);
        AcquirableSetting.STUBBORN_STEP = builder.define("STUBBORN_STEP", true);
        AcquirableSetting.INFECTIOUS_MALICE = builder.define("INFECTIOUS_MALICE", true);
        AcquirableSetting.I_SEE_YOU_NOW = builder.define("I_SEE_YOU_NOW", true);
        AcquirableSetting.MALICE_OUTBREAK = builder.define("MALICE_OUTBREAK", true);
        AcquirableSetting.PERCEIVED_MALICE = builder.define("PERCEIVED_MALICE", true);
        AcquirableSetting.VENGEANCE = builder.define("VENGEANCE", true);
        AcquirableSetting.BACK_AND_FILL = builder.define("BACK_AND_FILL", true);
        AcquirableSetting.AGONY_SCREAMER = builder.define("AGONY_SCREAMER", true);
        AcquirableSetting.CUTTING_WATERMELON_DREAM = builder.define("CUTTING_WATERMELON_DREAM", true);
        AcquirableSetting.INSANE_POET = builder.define("INSANE_POET", true);
        AcquirableSetting.PAPER_BRAIN = builder.define("PAPER_BRAIN", true);
        AcquirableSetting.SHOCK_THERAPY = builder.define("SHOCK_THERAPY", true);
        AcquirableSetting.BURNING_PHOBIA = builder.define("BURNING_PHOBIA", true);
        AcquirableSetting.CONSTRAINED_HEART = builder.define("CONSTRAINED_HEART", true);
        AcquirableSetting.DROWNING_PHOBIA = builder.define("DROWNING_PHOBIA", true);
        AcquirableSetting.PIERCING_FEVER = builder.define("PIERCING_FEVER", true);
        AcquirableSetting.DESTRUCTION_WORSHIP = builder.define("DESTRUCTION_WORSHIP", true);
        AcquirableSetting.PRAYER_OF_PAIN = builder.define("PRAYER_OF_PAIN", true);
        AcquirableSetting.FRESH_REVENGE = builder.define("FRESH_REVENGE", true);
        AcquirableSetting.HATRED_BLOODLINE = builder.define("HATRED_BLOODLINE", true);
        AcquirableSetting.OUTRAGEOUS_SPIRIT = builder.define("OUTRAGEOUS_SPIRIT", true);
        AcquirableSetting.RESENTFUL_SOUL = builder.define("RESENTFUL_SOUL", true);
        AcquirableSetting.TOO_RESENTFUL_TO_DIE = builder.define("TOO_RESENTFUL_TO_DIE", true);
        AcquirableSetting.MORIRS_DEATHWISH = builder.define("MORIRS_DEATHWISH", true);
        AcquirableSetting.MORIRS_LIFEBOUND = builder.define("MORIRS_LIFEBOUND", true);
        AcquirableSetting.GUIDENS_REGRET = builder.define("GUIDENS_REGRET", true);
        AcquirableSetting.LAST_SWEET_DREAM = builder.define("LAST_SWEET_DREAM", true);
        AcquirableSetting.NECESSARY_EVIL = builder.define("NECESSARY_EVIL", true);
        AcquirableSetting.SURVIVAL_RUSE = builder.define("SURVIVAL_RUSE", true);
        AcquirableSetting.SURVIVAL_SHORTCUT = builder.define("SURVIVAL_SHORTCUT", true);
        AcquirableSetting.CORRUPTED_KINDRED = builder.define("CORRUPTED_KINDRED", true);
        AcquirableSetting.LIGHTBURN_FUNGAL_PARASITIC = builder.define("LIGHTBURN_FUNGAL_PARASITIC", true);
        AcquirableSetting.PROTOTYPE_CHAOTIC = builder.define("PROTOTYPE_CHAOTIC", true);
        AcquirableSetting.PROTOTYPE_CHAOTIC_TYPE_BETA = builder.define("PROTOTYPE_CHAOTIC_TYPE_BETA", true);
        AcquirableSetting.SHADOWBORN = builder.define("SHADOWBORN", true);
        AcquirableSetting.SCHOLAR_OF_ORIGINAL_SIN = builder.define("SCHOLAR_OF_ORIGINAL_SIN", true);
        AcquirableSetting.ORIGINAL_SIN_EROSION = builder.define("ORIGINAL_SIN_EROSION", true);
        AcquirableSetting.BURIAL_OBJECT = builder.define("BURIAL_OBJECT", true);
        builder.pop();
        ACT_CONFIG = builder.build();
    }


}
