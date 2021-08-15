package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class LightburnFungalInfectionEffect extends Effect {
    public LightburnFungalInfectionEffect() {
        super(EffectType.HARMFUL, 16777196);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.LIGHTBURN_FUNGAL_INFECTION.get()) {
            entityLivingBaseIn.attackEntityFrom(CustomDamageSource.LIGHTBURN_FUNGAL_INFECTION, 3.0F);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
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
