package love.marblegate.flowingagony.effect.implicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.LivingEntity;


public class MiraculousEscapeForceEscapeEffect extends HarmfulBlankImplicitEffect {
    public MiraculousEscapeForceEscapeEffect() {
        super();
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_FORCE_ESCAPE.get()) {
            int duration = entityLivingBaseIn.getEffect(this).getDuration();
            if (duration % 40 > 25) {
                entityLivingBaseIn.setDeltaMovement(0, 2, 0);
            } else {
                entityLivingBaseIn.setDeltaMovement(entityLivingBaseIn.getLookAngle().x(), duration / 12.5, entityLivingBaseIn.getLookAngle().z());
            }
            entityLivingBaseIn.hurtMarked = true;
            /*entityLivingBaseIn.checkAndResetForcedChunkAdditionFlag();*/
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
