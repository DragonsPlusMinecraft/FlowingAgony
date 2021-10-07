package love.marblegate.flowingagony.effect.implicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.damagesource.DamageSource;

public class ShockTherapyImplicitEffect extends HarmfulBlankImplicitEffect {
    public ShockTherapyImplicitEffect() {
        super();
    }


    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.SHOCK_THERAPY_ENCHANTMENT_ACTIVE.get()) {
            LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(entityLivingBaseIn.level);
            lightningboltentity.setPos(entityLivingBaseIn.getX(), entityLivingBaseIn.getY(), entityLivingBaseIn.getZ());
            lightningboltentity.setVisualOnly(true);
            entityLivingBaseIn.level.addFreshEntity(lightningboltentity);
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
