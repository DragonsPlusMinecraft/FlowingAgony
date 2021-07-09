package love.marblegate.flowingagony.registry;

import love.marblegate.flowingagony.effect.*;
import love.marblegate.flowingagony.effect.special.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry {
    public static final DeferredRegister<Effect> EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, "flowingagony");
    public static final RegistryObject<Effect> cursed_hatred_effect = EFFECT.register("cursed_hatred", CursedHatredEffect::new);
    public static final RegistryObject<Effect> cursed_antipathy_effect = EFFECT.register("cursed_antipathy", CursedAntipathyEffect::new);
    public static final RegistryObject<Effect> extreme_hatred_effect = EFFECT.register("extreme_hatred", ExtremeHatredEffect::new);
    public static final RegistryObject<Effect> agony_resonance_effect = EFFECT.register("agony_resonance", AgonyResonanceEffect::new);
    public static final RegistryObject<Effect> been_resonated_effect = EFFECT.register("been_resonated", BeenResonatedEffect::new);
    public static final RegistryObject<Effect> curse_of_undead_effect = EFFECT.register("curse_of_undead", CurseOfUndeadEffect::new);
    public static final RegistryObject<Effect> let_me_savor_it_effect = EFFECT.register("let_me_savor_it", LetMeSavorItEffect::new);
    public static final RegistryObject<Effect> listen_to_me_singing_effect = EFFECT.register("listen_to_me_singing", ListenToMeSingingEffect::new);
    public static final RegistryObject<Effect> lightburn_fungal_infection_effect = EFFECT.register("lightburn_fungal_infection", LightburnFungalInfectionEffect::new);
    public static final RegistryObject<Effect> envious_being_effect = EFFECT.register("envious_being", () -> new EnviousBeingEffect()
            .addAttributesModifier(Attributes.ATTACK_DAMAGE,"444E310D-DE08-4D8D-A4B1-33B06CFB8703",1D, AttributeModifier.Operation.ADDITION));

    //Hidden Effect
    public static final RegistryObject<Effect> back_and_fill_enchantment_active_effect = EFFECT.register("back_and_fill_enchantment_active", () -> new BackAndFillImplicitEffect()
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"D6984A53-7DD1-4093-9878-345958E2E92A",-0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> potential_burst_enchantment_active_effect = EFFECT.register("potential_burst_enchantment_active", () -> new PotentialBurstImplicitEffect()
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"B22509E7-3596-430F-8F88-66A3EAFC99F7",0.01D, AttributeModifier.Operation.MULTIPLY_BASE));

    public static final RegistryObject<Effect> frivolous_step_enchantment_active_effect = EFFECT.register("frivolous_step_enchantment_active", () -> new FrivolousStepImplicitEffect()
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"412C831F-22EA-43B8-B74B-D172019AD3D2",0.15D, AttributeModifier.Operation.MULTIPLY_BASE));

    public static final RegistryObject<Effect> hatred_bloodline_enchantment_active_effect = EFFECT.register("hatred_bloodline_enchantment_active", () -> new HatredBloodlineImplicitEffect()
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"C164A28C-4A60-4957-B414-1DF28bb56C74",0.0083D, AttributeModifier.Operation.ADDITION)
            .addAttributesModifier(Attributes.ATTACK_SPEED,"F07D977E-F516-4D16-9088-11B70D847FFA",0.33D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> fresh_revenge_enchantment_active_effect = EFFECT.register("fresh_revenge_enchantment_active", () -> new FreshRevengeImplicitEffect()
            .addAttributesModifier(Attributes.ATTACK_DAMAGE,"f07d977e-f516-4d16-9088-11b70d847ffa",2D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> prototype_chaotic_enchantment_active_effect = EFFECT.register("prototype_chaotic_enchantment_active", () -> new FreshRevengeImplicitEffect()
            .addAttributesModifier(Attributes.MAX_HEALTH,"54405E8E-078E-46B0-9231-4B124C2964E6",1D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> miraculous_escape_enchantment_active_effect = EFFECT.register("miraculous_escape_encahntment_active", BeneficialBlankImplicitEffect::new);
    public static final RegistryObject<Effect> miraculous_escape_enchantment_force_escape_effect = EFFECT.register("miraculous_escape_encahntment_force_escape", MiraculousEscapeForceEscapeEffect::new);
    public static final RegistryObject<Effect> paper_brain_enchantment_active_effect = EFFECT.register("paper_brain_enchantment_active", () -> new PaperBrainImplicitEffect()
            .addAttributesModifier(Attributes.FOLLOW_RANGE,"831CF4BC-ED83-4072-A2A2-C115DD72317F",-0.96d, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"6E21DF28-A639-43E5-A189-D9ECFAE3AA39",-0.67D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> shock_therapy_enchantment_active_effect = EFFECT.register("shock_therapy_enchantment_active", () -> new ShockTherapyImplicitEffect()
            .addAttributesModifier(Attributes.FOLLOW_RANGE,"BA46BF78-B706-4FB0-9EA0-98978BB648B1",-0.96d, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"058D9210-1B26-4578-85C9-2CF67698F1E1",-0.67D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> thron_in_flesh_active_effect = EFFECT.register("thron_in_flesh_active", () -> new ThornInFleshImplicitEffect()
            .addAttributesModifier(Attributes.MOVEMENT_SPEED,"081F9BE2-F716-49B4-A579-62582F2A9DA8",-0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> eyesore_enchantment_active_effect = EFFECT.register("eyesore_enchantment_active", EyesoreImplicitEffect::new);


}
