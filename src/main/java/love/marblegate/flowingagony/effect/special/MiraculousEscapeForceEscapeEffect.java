package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class MiraculousEscapeForceEscapeEffect extends Effect {
    public MiraculousEscapeForceEscapeEffect() {
        super(EffectType.HARMFUL, 6881280);
    }

    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.miraculous_escape_encahntment_force_escape_effect.get()) {
            int duration = entityLivingBaseIn.getActivePotionEffect(this.getEffect()).getDuration();
            if(duration%40>25){
                entityLivingBaseIn.setMotion(0,2,0);
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
            else{
                entityLivingBaseIn.setMotion(entityLivingBaseIn.getLookVec().getX(),duration/12.5,entityLivingBaseIn.getLookVec().getZ());
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
        }
    }

    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
