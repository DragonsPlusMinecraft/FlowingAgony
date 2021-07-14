package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.DamageSource;

public class ShockTherapyImplicitEffect extends HarmfulBlankImplicitEffect {
    public ShockTherapyImplicitEffect() {
        super();
    }


    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.shock_therapy_enchantment_active.get()) {
            LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(entityLivingBaseIn.world);
            lightningboltentity.setPosition(entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY(),entityLivingBaseIn.getPosZ());
            lightningboltentity.setEffectOnly(true);
            entityLivingBaseIn.world.addEntity(lightningboltentity);
            entityLivingBaseIn.attackEntityFrom(DamageSource.LIGHTNING_BOLT.setDamageBypassesArmor(),entityLivingBaseIn.getRNG().nextInt(3)+2);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 10;
        if (duration > 0) {
            return duration % k == 0;
        }
        return false;
    }
}
