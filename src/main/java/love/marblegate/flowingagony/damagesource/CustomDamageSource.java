package love.marblegate.flowingagony.damagesource;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;

public class CustomDamageSource {
    public static final DamageSource CURSED_HATRED = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.curse_hatred");
    public static final DamageSource CURSED_ANTIPATHY = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.cursed_antipathy_effect").bypassArmor();
    public static final DamageSource LIGHTBURN_FUNGAL_INFECTION = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.lightburn_fungal_infection");
    public static final DamageSource AGONY_RESONANCE = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.agony_resonance");
    public static final DamageSource RHYTHM_OF_UNIVERSE = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.rhythm_of_universe");

    public static DamageSource causeCuttingWaterMelonDream(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.cutting_watermelon_dream", livingEntity);
    }

    public static DamageSource causeLetMeSavorItDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.let_me_savor_it", livingEntity);
    }

    public static DamageSource causeBurialObjectDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.burial_object_curse", livingEntity).bypassArmor();
    }

    public static DamageSource causePhobiaDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.phobia", livingEntity);

    }
}
