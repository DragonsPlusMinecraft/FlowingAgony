package love.marblegate.flowingagony.registry;

import love.marblegate.flowingagony.effect.*;
import love.marblegate.flowingagony.effect.special.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry {
    public static final DeferredRegister<Effect> EFFECT = new DeferredRegister<>(ForgeRegistries.POTIONS, "flowingagony");
    public static final RegistryObject<Effect> CURSED_HATRED = EFFECT.register("cursed_hatred", CursedHatredEffect::new);
    public static final RegistryObject<Effect> CURSED_ANTIPATHY = EFFECT.register("cursed_antipathy", CursedAntipathyEffect::new);
    public static final RegistryObject<Effect> EXTREME_HATRED = EFFECT.register("extreme_hatred", ExtremeHatredEffect::new);
    public static final RegistryObject<Effect> AGONY_RESONANCE = EFFECT.register("agony_resonance", AgonyResonanceEffect::new);
    public static final RegistryObject<Effect> BEEN_RESONATED = EFFECT.register("been_resonated", BeenResonatedEffect::new);
    public static final RegistryObject<Effect> CURSE_OF_UNDEAD = EFFECT.register("curse_of_undead", CurseOfUndeadEffect::new);
    public static final RegistryObject<Effect> LET_ME_SAVOR_IT = EFFECT.register("let_me_savor_it", LetMeSavorItEffect::new);
    public static final RegistryObject<Effect> LISTEN_TO_ME_SINGING = EFFECT.register("listen_to_me_singing", ListenToMeSingingEffect::new);
    public static final RegistryObject<Effect> LIGHTBURN_FUNGAL_INFECTION = EFFECT.register("lightburn_fungal_infection", LightburnFungalInfectionEffect::new);
    public static final RegistryObject<Effect> ENVIOUS_BEING = EFFECT.register("envious_being", () -> new EnviousBeingEffect()
            .addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE,"444E310D-DE08-4D8D-A4B1-33B06CFB8703",4D, AttributeModifier.Operation.ADDITION));

    //Hidden Effect
    public static final RegistryObject<Effect> BACK_AND_FILL_ENCHANTMENT_ACTIVE = EFFECT.register("back_and_fill_enchantment_active", () -> new BackAndFillImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"D6984A53-7DD1-4093-9878-345958E2E92A",-0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> POTENTIAL_BURST_ENCHANTMENT_ACTIVE = EFFECT.register("potential_burst_enchantment_active", () -> new PotentialBurstImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"B22509E7-3596-430F-8F88-66A3EAFC99F7",0.01D, AttributeModifier.Operation.MULTIPLY_BASE));

    public static final RegistryObject<Effect> FRIVOLOUS_STEP_ENCHANTMENT_ACTIVE = EFFECT.register("frivolous_step_enchantment_active", () -> new FrivolousStepImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"412C831F-22EA-43B8-B74B-D172019AD3D2",0.15D, AttributeModifier.Operation.MULTIPLY_BASE));

    public static final RegistryObject<Effect> HATRED_BLOODLINE_ENCHANTMENT_ACTIVE = EFFECT.register("hatred_bloodline_enchantment_active", () -> new HatredBloodlineImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"C164A28C-4A60-4957-B414-1DF28bb56C74",0.0083D, AttributeModifier.Operation.ADDITION)
            .addAttributesModifier(SharedMonsterAttributes.ATTACK_SPEED,"F07D977E-F516-4D16-9088-11B70D847FFA",0.33D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> FRESH_REVENGE_ENCHANTMENT_ACTIVE = EFFECT.register("fresh_revenge_enchantment_active", () -> new FreshRevengeImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE,"f07d977e-f516-4d16-9088-11b70d847ffa",2D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> PROTOTYPE_CHAOTIC_ENCHANTMENT_ACTIVE = EFFECT.register("prototype_chaotic_enchantment_active", () -> new FreshRevengeImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MAX_HEALTH,"54405E8E-078E-46B0-9231-4B124C2964E6",1D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> INSANE_POET_ENCHANTMENT_ACTIVE = EFFECT.register("insane_poet_enchantment_active", () -> new HarmfulBlankImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"4A2B62AC-37EC-4A06-94FF-ED6E2B2870B6",-0.8D, AttributeModifier.Operation.MULTIPLY_BASE));

    public static final RegistryObject<Effect> MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE = EFFECT.register("miraculous_escape_enchantment_active", BeneficialBlankImplicitEffect::new);
    public static final RegistryObject<Effect> MIRACULOUS_ESCAPE_ENCHANTMENT_FORCE_ESCAPE = EFFECT.register("miraculous_escape_enchantment_force_escape", MiraculousEscapeForceEscapeEffect::new);
    public static final RegistryObject<Effect> PAPER_BRAIN_ENCHANTMENT_ACTIVE = EFFECT.register("paper_brain_enchantment_active", () -> new PaperBrainImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.FOLLOW_RANGE,"831CF4BC-ED83-4072-A2A2-C115DD72317F",-0.96d, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"6E21DF28-A639-43E5-A189-D9ECFAE3AA39",-0.67D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> SHOCK_THERAPY_ENCHANTMENT_ACTIVE = EFFECT.register("shock_therapy_enchantment_active", () -> new ShockTherapyImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.FOLLOW_RANGE,"BA46BF78-B706-4FB0-9EA0-98978BB648B1",-0.96d, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"058D9210-1B26-4578-85C9-2CF67698F1E1",-0.67D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> THORN_IN_FLESH_ACTIVE = EFFECT.register("thorn_in_flesh_active", () -> new ThornInFleshImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"081F9BE2-F716-49B4-A579-62582F2A9DA8",-0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> THORN_IN_FLESH_ACTIVE_FOR_PLAYER = EFFECT.register("thorn_in_flesh_active_for_player", () -> new ThornInFleshImplicitEffect()
            .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED,"228345E7-66DC-4808-B325-82BE7728D3FC",-0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> EYESORE_ENCHANTMENT_ACTIVE = EFFECT.register("eyesore_enchantment_active", EyesoreImplicitEffect::new);


}
