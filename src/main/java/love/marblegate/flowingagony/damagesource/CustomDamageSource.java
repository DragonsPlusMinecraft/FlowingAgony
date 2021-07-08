package love.marblegate.flowingagony.damagesource;

import love.marblegate.flowingagony.damagesource.FlowingAgonyMobtoMobDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class CustomDamageSource {
    public static final DamageSource CURSED_HATRED = new DamageSource("flowingagony.curse_hatred");
    public static final DamageSource CURSED_ANTIPATHY = new DamageSource("flowingagony.cursed_antipathy_effect").setDamageBypassesArmor();
    public static final DamageSource LIGHTBURN_FUNGAL_INFECTION = new DamageSource("flowingagony.lightburn_fungal_infection");


    public static DamageSource causeLetMeSavorItDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.let_me_savor_it", livingEntity);
    }

    public static DamageSource causeBurialObjectDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.burial_object_curse", livingEntity).setDamageBypassesArmor();
    }
}
