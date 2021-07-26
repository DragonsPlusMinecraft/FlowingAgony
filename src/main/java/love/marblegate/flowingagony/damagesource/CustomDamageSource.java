package love.marblegate.flowingagony.damagesource;

import love.marblegate.flowingagony.damagesource.FlowingAgonyMobtoMobDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class CustomDamageSource {
    public static final DamageSource CURSED_HATRED = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.curse_hatred");
    public static final DamageSource CURSED_ANTIPATHY = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.cursed_antipathy_effect").setDamageBypassesArmor();
    public static final DamageSource LIGHTBURN_FUNGAL_INFECTION = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.lightburn_fungal_infection");
    public static final DamageSource CUTTING_WATERMELON_DREAM = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.cutting_watermelon_dream");
    public static final DamageSource AGONY_RESONANCE = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.agony_resonance");
    public static final DamageSource RHYTHM_OF_UNIVERSE = new FlowingAgonySimpleDeathMessageDamageSource("flowingagony.rhythm_of_universe");

    public static DamageSource causeLetMeSavorItDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.let_me_savor_it", livingEntity);
    }

    public static DamageSource causeBurialObjectDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.burial_object_curse", livingEntity).setDamageBypassesArmor();
    }

    public static DamageSource causePhobiaDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.phobia", livingEntity);

    }
}
