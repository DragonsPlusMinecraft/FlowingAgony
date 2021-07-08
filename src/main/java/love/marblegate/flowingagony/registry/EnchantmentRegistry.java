package love.marblegate.flowingagony.registry;

import love.marblegate.flowingagony.enchantment.diceoffraudcategory.*;
import love.marblegate.flowingagony.enchantment.flameofenvycategory.*;
import love.marblegate.flowingagony.enchantment.gloomyeracategory.*;
import love.marblegate.flowingagony.enchantment.innerpotentialcategory.*;
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
import net.minecraft.inventory.EquipmentSlotType;
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

    public static final RegistryObject<Enchantment> morirs_deathwish_enchantment = ENCHANTMENT.register("morirs_deathwish", () -> new MorirsDeathwishEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.values()));
    public static final RegistryObject<Enchantment> morirs_lifebound_enchantment = ENCHANTMENT.register("morirs_lifebound", () -> new MorirsLifeboundEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.values()));

    public static final RegistryObject<Enchantment> potential_burst_enchantment = ENCHANTMENT.register("potential_burst", () -> new PotentialBurstEnchantment(Enchantment.Rarity.UNCOMMON, new EquipmentSlotType[]{EquipmentSlotType.FEET}));
    public static final RegistryObject<Enchantment> stubborn_step_enchantment = ENCHANTMENT.register("stubborn_step", () -> new StubbornStepEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.LEGS}));
    public static final RegistryObject<Enchantment> frivolous_step_enchantment = ENCHANTMENT.register("frivolous_step", () -> new FrivolousStepEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.LEGS}));
    public static final RegistryObject<Enchantment> miraculous_escape_enchantment = ENCHANTMENT.register("miraculous_escape", () -> new MiraculousEscapeEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.FEET}));
    public static final RegistryObject<Enchantment> armor_up_enchantment = ENCHANTMENT.register("armor_up", () -> new ArmorUpEnchantment(Enchantment.Rarity.RARE, new EquipmentSlotType[]{EquipmentSlotType.CHEST}));

    public static final RegistryObject<Enchantment> regular_customer_program_enchantment = ENCHANTMENT.register("regular_customer_program", () -> new RegularCustomerProgramEnchantment(Enchantment.Rarity.RARE, new EquipmentSlotType[]{EquipmentSlotType.FEET}));
    public static final RegistryObject<Enchantment> cleansing_before_using_enchantment = ENCHANTMENT.register("cleansing_before_using", () -> new CleansingBeforeUsingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.values()));
    public static final RegistryObject<Enchantment> come_back_at_dusk_enchantment = ENCHANTMENT.register("come_back_at_dusk", () -> new ComeBackAtDuskEnchantment(Enchantment.Rarity.RARE, new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET}));
    public static final RegistryObject<Enchantment> dirty_money_enchantment = ENCHANTMENT.register("dirty_money", () -> new DirtyMoneyEnchantment(Enchantment.Rarity.VERY_RARE,new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET}));
    public static final RegistryObject<Enchantment> pilferage_creed_enchantment = ENCHANTMENT.register("pilferage_creed", () -> new PilferageCreedEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.FEET}));

    public static final RegistryObject<Enchantment> agony_screamer_enchantment = ENCHANTMENT.register("agony_screamer", () -> new AgonyScreamerEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> insane_poet_enchantment = ENCHANTMENT.register("insane_poet", () -> new InsanePoetEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> paper_brain_enchantment = ENCHANTMENT.register("paper_brain", () -> new PaperBrainEnchantment(Enchantment.Rarity.RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> shock_therapy_enchantment = ENCHANTMENT.register("shock_therapy", () -> new ShockTherapyEnchantment(Enchantment.Rarity.RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> cutting_watermelon_dream_enchantment = ENCHANTMENT.register("cutting_watermelon_dream", () -> new CuttingWatermelonDreamEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> drowning_phobia_enchantment = ENCHANTMENT.register("drowning_phobia", () -> new DrowningPhobiaEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.HEAD}));
    public static final RegistryObject<Enchantment> burning_phobia_enchantment = ENCHANTMENT.register("burning_phobia", () -> new BurningPhobiaEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.HEAD}));
    public static final RegistryObject<Enchantment> prayer_of_pain_enchantment = ENCHANTMENT.register("prayer_of_pain", () -> new PrayerOfPainEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.HEAD}));
    public static final RegistryObject<Enchantment> constrained_heart_enchantment = ENCHANTMENT.register("constrained_heart", () -> new ConstrainedHeartEnchantment(Enchantment.Rarity.UNCOMMON, new EquipmentSlotType[]{EquipmentSlotType.CHEST}));
    public static final RegistryObject<Enchantment> piercing_fever_enchantment = ENCHANTMENT.register("piercing_fever", () -> new PiercingFeverEnchantment(Enchantment.Rarity.UNCOMMON, new EquipmentSlotType[]{EquipmentSlotType.CHEST}));

    public static final RegistryObject<Enchantment> encious_kind_enchantment = ENCHANTMENT.register("encious_kind", () -> new EnciousKindEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.CHEST}));
    public static final RegistryObject<Enchantment> eyesore_enchantment = ENCHANTMENT.register("eyesore", () -> new EyesoreEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> thorn_in_flesh_enchantment = ENCHANTMENT.register("thorn_in_flesh", () -> new ThornInFleshEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> covert_knife_enchantment = ENCHANTMENT.register("covert_knife", () -> new CovertKnifeEnchantment(Enchantment.Rarity.RARE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> source_of_envy_enchantment = ENCHANTMENT.register("source_of_envy", () -> new SourceOfEnvyEnchantment(Enchantment.Rarity.VERY_RARE, new EquipmentSlotType[]{EquipmentSlotType.CHEST}));

}
