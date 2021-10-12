package love.marblegate.flowingagony.effect.explicit;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class LightburnFungalInfectionEffect extends MobEffect {
    public LightburnFungalInfectionEffect() {
        super(MobEffectCategory.HARMFUL, 16777196);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get()) {
            entityLivingBaseIn.hurt(CustomDamageSource.LIGHTBURN_FUNGAL_INFECTION, 3.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 40;
        if (amplifier > 0) {
            k /= (amplifier * amplifier);
        }
        if (k > 0) {
            return duration % k == 0;
        } else {
            return false;
        }
    }
}
