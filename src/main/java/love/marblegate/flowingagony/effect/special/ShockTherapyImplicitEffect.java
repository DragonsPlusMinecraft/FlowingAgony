package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class ShockTherapyImplicitEffect extends ImplicitBaseEffect {
    public ShockTherapyImplicitEffect() {
        super(EffectType.HARMFUL, 0);
    }


    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.shock_therapy_enchantment_active_effect.get()) {
            int rate = 9;
            if(amplifier==1) rate = 16;
            else if(amplifier==2) rate = 23;
            if(entityLivingBaseIn.getRNG().nextInt(100)<rate){
                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(entityLivingBaseIn.world);
                lightningboltentity.setPosition(entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY(),entityLivingBaseIn.getPosZ());
                lightningboltentity.setEffectOnly(true);
                entityLivingBaseIn.world.addEntity(lightningboltentity);
                entityLivingBaseIn.attackEntityFrom(DamageSource.LIGHTNING_BOLT.setDamageBypassesArmor(),2);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 5;
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return false;
        }
    }
}
