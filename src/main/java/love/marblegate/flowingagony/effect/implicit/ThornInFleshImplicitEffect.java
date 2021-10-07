package love.marblegate.flowingagony.effect.implicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;

public class ThornInFleshImplicitEffect extends HarmfulBlankImplicitEffect {
    public ThornInFleshImplicitEffect() {
        super();
    }

    //FIXME
    // - need test
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.THORN_IN_FLESH_ACTIVE.get()) {
            int duration = entityLivingBaseIn.getEffect(this).getDuration();
            if (duration % 60 < 10) {
                if (entityLivingBaseIn instanceof Player) {
                    entityLivingBaseIn.hurt(DamageSource.thorns(entityLivingBaseIn), 1);
                } else {
                    entityLivingBaseIn.setDeltaMovement(0, entityLivingBaseIn.getDeltaMovement().y, 0);
                    entityLivingBaseIn.hurtMarked = true;
                    /*entityLivingBaseIn.checkAndResetForcedChunkAdditionFlag();*/
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
