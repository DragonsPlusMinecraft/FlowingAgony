package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class CursedAntipathyEffect extends Effect {
    public CursedAntipathyEffect() {
        super(EffectType.HARMFUL, 18432);
    }

    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.cursed_antipathy_effect.get()) {
            entityLivingBaseIn.attackEntityFrom((new DamageSource("flowingagony.cursed_antipathy_effect")).setDamageBypassesArmor(), 1.0F);
        }
    }

    public boolean isReady(int duration, int amplifier) {
        int k = 40;
        if(amplifier>0){
            k /= (amplifier * amplifier);
        }
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return false;
        }
    }
}
