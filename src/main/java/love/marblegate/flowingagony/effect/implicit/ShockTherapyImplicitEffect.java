package love.marblegate.flowingagony.effect.implicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;

public class ShockTherapyImplicitEffect extends HarmfulBlankImplicitEffect {
    public ShockTherapyImplicitEffect() {
        super();
    }


    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.SHOCK_THERAPY_ENCHANTMENT_ACTIVE.get()) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(entityLivingBaseIn.level);
            lightningBolt.setPos(entityLivingBaseIn.getX(), entityLivingBaseIn.getY(), entityLivingBaseIn.getZ());
            lightningBolt.setVisualOnly(true);
            entityLivingBaseIn.level.addFreshEntity(lightningBolt);
            entityLivingBaseIn.hurt(DamageSource.LIGHTNING_BOLT.bypassArmor(), entityLivingBaseIn.getRandom().nextInt(3) + 2);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 10;
        if (duration > 0) {
            return duration % k == 0;
        }
        return false;
    }
}
