package love.marblegate.flowingagony.effect.explicit;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ListenToMeSingingEffect extends MobEffect {
    public ListenToMeSingingEffect() {
        super(MobEffectCategory.HARMFUL, 6881280);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.LISTEN_TO_ME_SINGING.get()) {
            int duration = entityLivingBaseIn.getEffect(this).getDuration();
            if (duration % 40 > 25) {
                entityLivingBaseIn.setDeltaMovement(0, 0.41, 0);
                entityLivingBaseIn.hurtMarked = true;
                /*entityLivingBaseIn.checkAndResetForcedChunkAdditionFlag();*/
            } else if (duration % 40 > 7) {
                entityLivingBaseIn.setDeltaMovement(0, 0.0, 0);
                entityLivingBaseIn.hurtMarked = true;
                /*entityLivingBaseIn.checkAndResetForcedChunkAdditionFlag();*/
            } else if (duration % 40 > 0) {
                entityLivingBaseIn.setDeltaMovement(0, -0.79, 0);
                entityLivingBaseIn.hurtMarked = true;
                /*entityLivingBaseIn.checkAndResetForcedChunkAdditionFlag();*/
            } else if (duration % 40 == 0) {
                entityLivingBaseIn.setDeltaMovement(0, 0, 0);
                entityLivingBaseIn.hurtMarked = true;
                /*entityLivingBaseIn.checkAndResetForcedChunkAdditionFlag();*/
                float damage = entityLivingBaseIn.getMaxHealth() * 0.2f + entityLivingBaseIn.getHealth() * 0.5f;
                damage = Math.min(getMaxDamage(amplifier), damage);
                damage = Math.max(getMinDamage(amplifier), damage);
                entityLivingBaseIn.hurt(CustomDamageSource.RHYTHM_OF_UNIVERSE, damage);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    int getMaxDamage(int amplifier) {
        if (amplifier == 0) return 9;
        else return 12 + amplifier * 2;
    }

    int getMinDamage(int amplifier) {
        if (amplifier < 3) return 1;
        else if (amplifier == 3) return 2;
        else return 3;
    }
}
