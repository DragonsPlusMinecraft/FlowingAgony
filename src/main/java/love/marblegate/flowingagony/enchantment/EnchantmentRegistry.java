package love.marblegate.flowingagony.enchantment;

import love.marblegate.flowingagony.enchantment.diceoffraudcategory.*;
import love.marblegate.flowingagony.enchantment.flameofenvycategory.*;
import love.marblegate.flowingagony.enchantment.gloomyeracategory.*;
import love.marblegate.flowingagony.enchantment.innerpotentialcategory.*;
import love.marblegate.flowingagony.enchantment.lastwish.GuidensRegretEnchantment;
import love.marblegate.flowingagony.enchantment.lastwish.LastSweetDreamEnchantment;
import love.marblegate.flowingagony.enchantment.lastwish.MorirsDeathwishEnchantment;
import love.marblegate.flowingagony.enchantment.lastwish.MorirsLifeboundEnchantment;
import love.marblegate.flowingagony.enchantment.lensofmalicecategory.*;
import love.marblegate.flowingagony.enchantment.madeofmadnesscategory.*;
import love.marblegate.flowingagony.enchantment.madeofsufferingcategory.*;
import love.marblegate.flowingagony.enchantment.rootedinhatredcategory.*;
import love.marblegate.flowingagony.enchantment.survivaltrickscategory.NecessaryEvilEnchantment;
import love.marblegate.flowingagony.enchantment.survivaltrickscategory.SurvivalRuseEnchantment;
import love.marblegate.flowingagony.enchantment.survivaltrickscategory.SurvivalShortcutEnchantment;
import love.marblegate.flowingagony.enchantment.themistakenscategory.*;
import love.marblegate.flowingagony.enchantment.themistakenscategory.curse.BurialObjectCurse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class EnchantmentRegistry {

    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "flowingagony");

    public static final RegistryObject<Enchantment> VENGEANCE = ENCHANTMENT.register("vengeance", VengeanceEnchantment::new);
    public static final RegistryObject<Enchantment> PERCEIVED_MALICE = ENCHANTMENT.register("perceived_malice", PerceivedMaliceEnchantment::new);
    public static final RegistryObject<Enchantment> MALICE_OUTBREAK = ENCHANTMENT.register("malice_outbreak", MaliceOutbreakEnchantment::new);
    public static final RegistryObject<Enchantment> INFECTIOUS_MALICE = ENCHANTMENT.register("infectious_malice", InfectiousMaliceEnchantment::new);
    public static final RegistryObject<Enchantment> I_SEE_YOU_NOW = ENCHANTMENT.register("i_see_you_now", ISeeYouNowEnchantment::new);
    public static final RegistryObject<Enchantment> BACK_AND_FILL = ENCHANTMENT.register("back_and_fill", BackAndFillEnchantment::new);

    public static final RegistryObject<Enchantment> RESENTFUL_SOUL = ENCHANTMENT.register("resentful_soul", ResentfulSoulEnchantment::new);
    public static final RegistryObject<Enchantment> TOO_RESENTFUL_TO_DIE = ENCHANTMENT.register("too_resentful_to_die", TooResentfulToDieEnchantment::new);
    public static final RegistryObject<Enchantment> OUTRAGEOUS_SPIRIT = ENCHANTMENT.register("outrageous_spirit", OutrageoutSpiritEnchantment::new);
    public static final RegistryObject<Enchantment> HATRED_BLOODLINE = ENCHANTMENT.register("hatred_bloodline", HatredBloodlineEnchantment::new);
    public static final RegistryObject<Enchantment> FRESH_REVENGE = ENCHANTMENT.register("fresh_revenge", FreshRevengeEnchantment::new);

    public static final RegistryObject<Enchantment> SHADOWBORN = ENCHANTMENT.register("shadowborn", ShadowbornEnchantment::new);
    public static final RegistryObject<Enchantment> PROTOTYPE_CHAOTIC = ENCHANTMENT.register("prototype_chaotic", PrototypeChaoticEnchantment::new);
    public static final RegistryObject<Enchantment> PROTOTYPE_CHAOTIC_TYPE_BETA = ENCHANTMENT.register("prototype_chaotic_type_beta", PrototypeChaoticTypeBetaEnchantment::new);
    public static final RegistryObject<Enchantment> CORRUPTED_KINDRED = ENCHANTMENT.register("corrupted_kindred", CorrruptedKindredEnchantment::new);
    public static final RegistryObject<Enchantment> LIGHTBURN_FUNGAL_PARASITIC = ENCHANTMENT.register("lightburn_fungal_parasitic", LightburnFungalParasiticEnchantment::new);
    public static final RegistryObject<Enchantment> SCHOLAR_OF_ORIGINAL_SIN = ENCHANTMENT.register("scholar_of_original_sin", ScholarOfOriginalSinEnchantment::new);
    public static final RegistryObject<Enchantment> ORIGINAL_SIN_EROSION = ENCHANTMENT.register("original_sin_erosion", OriginalSinErosionEnchantment::new);
    public static final RegistryObject<Enchantment> BURIAL_OBJECT = ENCHANTMENT.register("burial_object", BurialObjectCurse::new);

    public static final RegistryObject<Enchantment> TRICKSTER = ENCHANTMENT.register("trickster", TricksterEnchantment::new);
    public static final RegistryObject<Enchantment> AN_ENCHANTED_GOLDEN_APPLE_A_DAY = ENCHANTMENT.register("an_enchanted_golden_apple_a_day", AnEnchantedGoldenAppleADayEnchantment::new);
    public static final RegistryObject<Enchantment> DEATHPUNK = ENCHANTMENT.register("deathpunk", DeathpunkEnchantment::new);
    public static final RegistryObject<Enchantment> SAVOR_THE_TASTED = ENCHANTMENT.register("savor_the_tasted", SavorTheTastedEnchantment::new);
    public static final RegistryObject<Enchantment> EXOTIC_HEALER = ENCHANTMENT.register("exotic_healer", ExoticHealerEnchantment::new);

    public static final RegistryObject<Enchantment> SURVIVAL_SHORTCUT = ENCHANTMENT.register("survival_shortcut", SurvivalShortcutEnchantment::new);
    public static final RegistryObject<Enchantment> SURVIVAL_RUSE = ENCHANTMENT.register("survival_ruse", SurvivalRuseEnchantment::new);
    public static final RegistryObject<Enchantment> NECESSARY_EVIL = ENCHANTMENT.register("necessary_evil", NecessaryEvilEnchantment::new);

    public static final RegistryObject<Enchantment> MORIRS_DEATHWISH = ENCHANTMENT.register("morirs_deathwish", MorirsDeathwishEnchantment::new);
    public static final RegistryObject<Enchantment> MORIRS_LIFEBOUND = ENCHANTMENT.register("morirs_lifebound", MorirsLifeboundEnchantment::new);
    public static final RegistryObject<Enchantment> GUIDENS_REGRET = ENCHANTMENT.register("guidens_regret", GuidensRegretEnchantment::new);
    public static final RegistryObject<Enchantment> LAST_SWEET_DREAM = ENCHANTMENT.register("last_sweet_dream", LastSweetDreamEnchantment::new);

    public static final RegistryObject<Enchantment> POTENTIAL_BURST = ENCHANTMENT.register("potential_burst", PotentialBurstEnchantment::new);
    public static final RegistryObject<Enchantment> STUBBORN_STEP = ENCHANTMENT.register("stubborn_step", StubbornStepEnchantment::new);
    public static final RegistryObject<Enchantment> FRIVOLOUS_STEP = ENCHANTMENT.register("frivolous_step", FrivolousStepEnchantment::new);
    public static final RegistryObject<Enchantment> MIRACULOUS_ESCAPE = ENCHANTMENT.register("miraculous_escape", MiraculousEscapeEnchantment::new);
    public static final RegistryObject<Enchantment> ARMOR_UP = ENCHANTMENT.register("armor_up", ArmorUpEnchantment::new);

    public static final RegistryObject<Enchantment> REGULAR_CUSTOMER_PROGRAM = ENCHANTMENT.register("regular_customer_program", RegularCustomerProgramEnchantment::new);
    public static final RegistryObject<Enchantment> CLEANSING_BEFORE_USING = ENCHANTMENT.register("cleansing_before_using", CleansingBeforeUsingEnchantment::new);
    public static final RegistryObject<Enchantment> COME_BACK_AT_DUSK = ENCHANTMENT.register("come_back_at_dusk", ComeBackAtDuskEnchantment::new);
    public static final RegistryObject<Enchantment> DIRTY_MONEY = ENCHANTMENT.register("dirty_money", DirtyMoneyEnchantment::new);
    public static final RegistryObject<Enchantment> PILFERAGE_CREED = ENCHANTMENT.register("pilferage_creed", PilferageCreedEnchantment::new);
    public static final RegistryObject<Enchantment> CAREFULLY_IDENTIFIED = ENCHANTMENT.register("carefully_identified", CarefullyIdentifiedEnchantment::new);
    public static final RegistryObject<Enchantment> NIMBLE_FINGER = ENCHANTMENT.register("nimble_finger", NimbleFingerEnchantment::new);

    public static final RegistryObject<Enchantment> AGONY_SCREAMER = ENCHANTMENT.register("agony_screamer", AgonyScreamerEnchantment::new);
    public static final RegistryObject<Enchantment> INSANE_POET = ENCHANTMENT.register("insane_poet", InsanePoetEnchantment::new);
    public static final RegistryObject<Enchantment> PAPER_BRAIN = ENCHANTMENT.register("paper_brain", PaperBrainEnchantment::new);
    public static final RegistryObject<Enchantment> SHOCK_THERAPY = ENCHANTMENT.register("shock_therapy", ShockTherapyEnchantment::new);
    public static final RegistryObject<Enchantment> CUTTING_WATERMELON_DREAM = ENCHANTMENT.register("cutting_watermelon_dream", CuttingWatermelonDreamEnchantment::new);

    public static final RegistryObject<Enchantment> DROWNING_PHOBIA = ENCHANTMENT.register("drowning_phobia", DrowningPhobiaEnchantment::new);
    public static final RegistryObject<Enchantment> BURNING_PHOBIA = ENCHANTMENT.register("burning_phobia", BurningPhobiaEnchantment::new);
    public static final RegistryObject<Enchantment> PRAYER_OF_PAIN = ENCHANTMENT.register("prayer_of_pain", PrayerOfPainEnchantment::new);
    public static final RegistryObject<Enchantment> CONSTRAINED_HEART = ENCHANTMENT.register("constrained_heart", ConstrainedHeartEnchantment::new);
    public static final RegistryObject<Enchantment> PIERCING_FEVER = ENCHANTMENT.register("piercing_fever", PiercingFeverEnchantment::new);
    public static final RegistryObject<Enchantment> DESTRUCTION_WORSHIP = ENCHANTMENT.register("destruction_worship", DestructionWorshipEnchantment::new);

    public static final RegistryObject<Enchantment> ENVIOUS_KIND = ENCHANTMENT.register("envious_kind", EnviousKindEnchantment::new);
    public static final RegistryObject<Enchantment> EYESORE = ENCHANTMENT.register("eyesore", EyesoreEnchantment::new);
    public static final RegistryObject<Enchantment> THORN_IN_FLESH = ENCHANTMENT.register("thorn_in_flesh", ThornInFleshEnchantment::new);
    public static final RegistryObject<Enchantment> COVERT_KNIFE = ENCHANTMENT.register("covert_knife", CovertKnifeEnchantment::new);
    public static final RegistryObject<Enchantment> SOURCE_OF_ENVY = ENCHANTMENT.register("source_of_envy", SourceOfEnvyEnchantment::new);

}
