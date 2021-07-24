package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

public class ThornInFleshImplicitEffect extends HarmfulBlankImplicitEffect {
    public ThornInFleshImplicitEffect() {
        super();
    }


    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.THORN_IN_FLESH_ACTIVE.get()) {
            int duration = entityLivingBaseIn.getActivePotionEffect(getEffect()).getDuration();
            if(duration%60<10){
                if(entityLivingBaseIn instanceof PlayerEntity){
                    entityLivingBaseIn.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBaseIn),1);
                } else {
                    entityLivingBaseIn.setMotion(0,entityLivingBaseIn.getMotion().y,0);
                    entityLivingBaseIn.velocityChanged=true;
                    entityLivingBaseIn.markPositionDirty();
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
