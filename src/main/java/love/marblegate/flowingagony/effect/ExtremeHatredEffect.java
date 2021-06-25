package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ExtremeHatredEffect extends Effect {
    public ExtremeHatredEffect() {
        super(EffectType.HARMFUL, 16711680);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.extreme_hatred_effect.get()) {
            if(entityLivingBaseIn instanceof PlayerEntity){
                PlayerUtil.crazilyComsumeFoodLevel((PlayerEntity) entityLivingBaseIn);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = (15/(amplifier+1));
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return false;
        }
    }
}
