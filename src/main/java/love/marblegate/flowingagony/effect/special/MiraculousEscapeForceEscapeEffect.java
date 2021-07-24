package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;


public class MiraculousEscapeForceEscapeEffect extends HarmfulBlankImplicitEffect {
    public MiraculousEscapeForceEscapeEffect() {
        super();
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_FORCE_ESCAPE.get()) {
            int duration = entityLivingBaseIn.getActivePotionEffect(getEffect()).getDuration();
            if(duration%40>25){
                entityLivingBaseIn.setMotion(0,2,0);
            }
            else{
                entityLivingBaseIn.setMotion(entityLivingBaseIn.getLookVec().getX(),duration/12.5,entityLivingBaseIn.getLookVec().getZ());
            }
            entityLivingBaseIn.velocityChanged=true;
            entityLivingBaseIn.markPositionDirty();
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
