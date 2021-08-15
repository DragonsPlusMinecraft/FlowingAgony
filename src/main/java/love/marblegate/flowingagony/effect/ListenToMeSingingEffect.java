package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ListenToMeSingingEffect extends Effect {
    public ListenToMeSingingEffect() {
        super(EffectType.HARMFUL, 6881280);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.LISTEN_TO_ME_SINGING.get()) {
            int duration = entityLivingBaseIn.getActivePotionEffect(getEffect()).getDuration();
            if (duration % 40 > 25) {
                entityLivingBaseIn.setMotion(0, 0.41, 0);
                entityLivingBaseIn.velocityChanged = true;
                entityLivingBaseIn.markPositionDirty();
            } else if (duration % 40 > 7) {
                entityLivingBaseIn.setMotion(0, 0.0, 0);
                entityLivingBaseIn.velocityChanged = true;
                entityLivingBaseIn.markPositionDirty();
            } else if (duration % 40 > 0) {
                entityLivingBaseIn.setMotion(0, -0.79, 0);
                entityLivingBaseIn.velocityChanged = true;
                entityLivingBaseIn.markPositionDirty();
            } else if (duration % 40 == 0) {
                entityLivingBaseIn.setMotion(0, 0, 0);
                entityLivingBaseIn.velocityChanged = true;
                entityLivingBaseIn.markPositionDirty();
                float damage = entityLivingBaseIn.getMaxHealth() * 0.2f + entityLivingBaseIn.getHealth() * 0.5f;
                damage = Math.min(getMaxDamage(amplifier), damage);
                damage = Math.max(getMinDamage(amplifier), damage);
                entityLivingBaseIn.attackEntityFrom(CustomDamageSource.RHYTHM_OF_UNIVERSE, damage);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
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
