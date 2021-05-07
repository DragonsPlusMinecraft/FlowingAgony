package love.marblegate.flowingagony.damagesource;

import love.marblegate.flowingagony.damagesource.FlowingAgonyMobtoMobDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class CustomDamageSource {
    public static DamageSource causeLetMeSavorItDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.let_me_savor_it", livingEntity);
    }

    public static DamageSource causeBurialObjectDamage(LivingEntity livingEntity) {
        return new FlowingAgonyMobtoMobDamageSource("flowingagony.burial_object_curse", livingEntity).setDamageBypassesArmor();
    }
}
