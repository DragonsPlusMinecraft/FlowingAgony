package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;


public class MiraculousEscapeForceEscapeEffect extends HarmfulBlankImplicitEffect {
    public MiraculousEscapeForceEscapeEffect() {
        super();
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.miraculous_escape_enchantment_force_escape.get()) {
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

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
