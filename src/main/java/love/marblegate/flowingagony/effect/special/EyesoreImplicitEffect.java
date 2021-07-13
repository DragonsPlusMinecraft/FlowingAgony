package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;

public class EyesoreImplicitEffect extends ImplicitBaseEffect {
    public EyesoreImplicitEffect() {
        super();
    }


    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.eyesore_enchantment_active.get()) {
            entityLivingBaseIn.world.createExplosion(entityLivingBaseIn, entityLivingBaseIn.getPosX(), entityLivingBaseIn.getPosY(), entityLivingBaseIn.getPosZ(), 1, Explosion.Mode.NONE);
            entityLivingBaseIn.attackEntityFrom(DamageSource.causeExplosionDamage(entityLivingBaseIn), 3 + amplifier * 2);
            entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60 + 20 * amplifier));
            entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60 + 20 * amplifier));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration == 1;
    }
}
