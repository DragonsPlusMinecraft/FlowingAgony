package love.marblegate.flowingagony.effect.implicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;

public class EyesoreImplicitEffect extends ImplicitBaseEffect {
    public EyesoreImplicitEffect() {
        super();
    }


    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.EYESORE_ENCHANTMENT_ACTIVE.get()) {
            entityLivingBaseIn.level.explode(entityLivingBaseIn, entityLivingBaseIn.getX(), entityLivingBaseIn.getY(), entityLivingBaseIn.getZ(), 1, Explosion.BlockInteraction.NONE);
            entityLivingBaseIn.hurt(DamageSource.explosion(entityLivingBaseIn), 3 + amplifier * 2);
            entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60 + 20 * amplifier));
            entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 + 20 * amplifier));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 1;
    }
}
