package love.marblegate.flowingagony.registry;

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
import love.marblegate.flowingagony.enchantment.survivaltrickscategory.*;
import love.marblegate.flowingagony.enchantment.themistakenscategory.*;
import love.marblegate.flowingagony.enchantment.themistakenscategory.curse.BurialObjectCurse;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class EnchantmentRegistry {

    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "flowingagony");

    public static final RegistryObject<Enchantment> vengeance = ENCHANTMENT.register("vengeance", VengeanceEnchantment::new);
    public static final RegistryObject<Enchantment> perceived_malice = ENCHANTMENT.register("perceived_malice", PerceivedMaliceEnchantment::new);
    public static final RegistryObject<Enchantment> malice_outbreak = ENCHANTMENT.register("malice_outbreak", MaliceOutbreakEnchantment::new);
    public static final RegistryObject<Enchantment> infectious_malice = ENCHANTMENT.register("infectious_malice", InfectiousMaliceEnchantment::new);
    public static final RegistryObject<Enchantment> i_see_you_now = ENCHANTMENT.register("i_see_you_now", ISeeYouNowEnchantment::new);
    public static final RegistryObject<Enchantment> back_and_fill = ENCHANTMENT.register("back_and_fill", BackAndFillEnchantment::new);

    public static final RegistryObject<Enchantment> resentful_soul = ENCHANTMENT.register("resentful_soul", ResentfulSoulEnchantment::new);
    public static final RegistryObject<Enchantment> too_resentful_to_die = ENCHANTMENT.register("too_resentful_to_die", TooResentfulToDieEnchantment::new);
    public static final RegistryObject<Enchantment> outrageous_spirit = ENCHANTMENT.register("outrageous_spirit", OutrageoutSpiritEnchantment::new);
    public static final RegistryObject<Enchantment> hatred_bloodline = ENCHANTMENT.register("hatred_bloodline", HatredBloodlineEnchantment::new);
    public static final RegistryObject<Enchantment> fresh_revenge = ENCHANTMENT.register("fresh_revenge", FreshRevengeEnchantment::new);

    public static final RegistryObject<Enchantment> shadowborn = ENCHANTMENT.register("shadowborn", ShadowbornEnchantment::new);
    public static final RegistryObject<Enchantment> prototype_chaotic = ENCHANTMENT.register("prototype_chaotic", PrototypeChaoticEnchantment::new);
    public static final RegistryObject<Enchantment> prototype_chaotic_type_beta = ENCHANTMENT.register("prototype_chaotic_type_beta", PrototypeChaoticTypeBetaEnchantment::new);
    public static final RegistryObject<Enchantment> corrupted_kindred = ENCHANTMENT.register("corrupted_kindred", CorrruptedKindredEnchantment::new);
    public static final RegistryObject<Enchantment> lightburn_fungal_parasitic = ENCHANTMENT.register("lightburn_fungal_parasitic", LightburnFungalParasiticEnchantment::new);
    public static final RegistryObject<Enchantment> scholar_of_original_sin = ENCHANTMENT.register("scholar_of_original_sin", ScholarOfOriginalSinEnchantment::new);
    public static final RegistryObject<Enchantment> original_sin_erosion = ENCHANTMENT.register("original_sin_erosion", OriginalSinErosionEnchantment::new);
    public static final RegistryObject<Enchantment> burial_object = ENCHANTMENT.register("burial_object", BurialObjectCurse::new);

    public static final RegistryObject<Enchantment> trickster = ENCHANTMENT.register("trickster", TricksterEnchantment::new);
    public static final RegistryObject<Enchantment> an_enchanted_apple_a_day = ENCHANTMENT.register("an_enchanted_golden_apple_a_day", AnEnchantedGoldenAppleADayEnchantment::new);
    public static final RegistryObject<Enchantment> deathpunk = ENCHANTMENT.register("deathpunk", DeathpunkEnchantment::new);
    public static final RegistryObject<Enchantment> savor_the_taste = ENCHANTMENT.register("savor_the_tasted", SavorTheTastedEnchantment::new);
    public static final RegistryObject<Enchantment> exotic_healer = ENCHANTMENT.register("exotic_healer", ExoticHealerEnchantment::new);

    public static final RegistryObject<Enchantment> survival_shortcut = ENCHANTMENT.register("survival_shortcut", SurvivalShortcutEnchantment::new);
    public static final RegistryObject<Enchantment> survival_ruse = ENCHANTMENT.register("survival_ruse", SurvivalRuseEnchantment::new);
    public static final RegistryObject<Enchantment> necessary_evil = ENCHANTMENT.register("necessary_evil", NecessaryEvilEnchantment::new);

    public static final RegistryObject<Enchantment> morirs_deathwish = ENCHANTMENT.register("morirs_deathwish", MorirsDeathwishEnchantment::new);
    public static final RegistryObject<Enchantment> morirs_lifebound = ENCHANTMENT.register("morirs_lifebound", MorirsLifeboundEnchantment::new);
    public static final RegistryObject<Enchantment> guidens_regret = ENCHANTMENT.register("guidens_regret", GuidensRegretEnchantment::new);
    public static final RegistryObject<Enchantment> last_sweet_dream = ENCHANTMENT.register("last_sweet_dream", LastSweetDreamEnchantment::new);

    public static final RegistryObject<Enchantment> potential_burst = ENCHANTMENT.register("potential_burst", PotentialBurstEnchantment::new);
    public static final RegistryObject<Enchantment> stubborn_step = ENCHANTMENT.register("stubborn_step", StubbornStepEnchantment::new);
    public static final RegistryObject<Enchantment> frivolous_step = ENCHANTMENT.register("frivolous_step", FrivolousStepEnchantment::new);
    public static final RegistryObject<Enchantment> miraculous_escape = ENCHANTMENT.register("miraculous_escape", MiraculousEscapeEnchantment::new);
    public static final RegistryObject<Enchantment> armor_up = ENCHANTMENT.register("armor_up", ArmorUpEnchantment::new);

    public static final RegistryObject<Enchantment> regular_customer_program = ENCHANTMENT.register("regular_customer_program", RegularCustomerProgramEnchantment::new);
    public static final RegistryObject<Enchantment> cleansing_before_using = ENCHANTMENT.register("cleansing_before_using", CleansingBeforeUsingEnchantment::new);
    public static final RegistryObject<Enchantment> come_back_at_dusk = ENCHANTMENT.register("come_back_at_dusk", ComeBackAtDuskEnchantment::new);
    public static final RegistryObject<Enchantment> dirty_money = ENCHANTMENT.register("dirty_money", DirtyMoneyEnchantment::new);
    public static final RegistryObject<Enchantment> pilferage_creed = ENCHANTMENT.register("pilferage_creed", PilferageCreedEnchantment::new);
    public static final RegistryObject<Enchantment> carefully_identified = ENCHANTMENT.register("carefully_identified", CarefullyIdentifiedEnchantment::new);
    public static final RegistryObject<Enchantment> nimble_finger = ENCHANTMENT.register("nimble_finger", NimbleFingerEnchantment::new);

    public static final RegistryObject<Enchantment> agony_screamer = ENCHANTMENT.register("agony_screamer", AgonyScreamerEnchantment::new);
    public static final RegistryObject<Enchantment> insane_poet = ENCHANTMENT.register("insane_poet", InsanePoetEnchantment::new);
    public static final RegistryObject<Enchantment> paper_brain = ENCHANTMENT.register("paper_brain", PaperBrainEnchantment::new);
    public static final RegistryObject<Enchantment> shock_therapy = ENCHANTMENT.register("shock_therapy", ShockTherapyEnchantment::new);
    public static final RegistryObject<Enchantment> cutting_watermelon_dream = ENCHANTMENT.register("cutting_watermelon_dream", CuttingWatermelonDreamEnchantment::new);

    public static final RegistryObject<Enchantment> drowning_phobia = ENCHANTMENT.register("drowning_phobia", DrowningPhobiaEnchantment::new);
    public static final RegistryObject<Enchantment> burning_phobia = ENCHANTMENT.register("burning_phobia", BurningPhobiaEnchantment::new);
    public static final RegistryObject<Enchantment> prayer_of_pain = ENCHANTMENT.register("prayer_of_pain", PrayerOfPainEnchantment::new);
    public static final RegistryObject<Enchantment> constrained_heart = ENCHANTMENT.register("constrained_heart", ConstrainedHeartEnchantment::new);
    public static final RegistryObject<Enchantment> piercing_fever = ENCHANTMENT.register("piercing_fever", PiercingFeverEnchantment::new);
    public static final RegistryObject<Enchantment> destruction_worship = ENCHANTMENT.register("destruction_worship", DestructionWorshipEnchantment::new);

    //registry name of envious kind enchantment is a "legacy typo".
    //for maximum compatibility when player update to new version, we do not change it.
    public static final RegistryObject<Enchantment> envious_kind = ENCHANTMENT.register("encious_kind", EnviousKindEnchantment::new);
    public static final RegistryObject<Enchantment> eyesore = ENCHANTMENT.register("eyesore", EyesoreEnchantment::new);
    public static final RegistryObject<Enchantment> thorn_in_flesh = ENCHANTMENT.register("thorn_in_flesh", ThornInFleshEnchantment::new);
    public static final RegistryObject<Enchantment> covert_knife = ENCHANTMENT.register("covert_knife", CovertKnifeEnchantment::new);
    public static final RegistryObject<Enchantment> source_of_envy = ENCHANTMENT.register("source_of_envy", SourceOfEnvyEnchantment::new);

}
