package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.registry.EffectRegistry;
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
        if (this == EffectRegistry.EXTREME_HATRED.get()) {
            if (entityLivingBaseIn instanceof PlayerEntity) {
                crazilyComsumeFoodLevel((PlayerEntity) entityLivingBaseIn);
            }
        }
    }

    static void crazilyComsumeFoodLevel(PlayerEntity player) {
        player.getFoodStats().addExhaustion(4);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = (15 / (amplifier + 1));
        if (k > 0) {
            return duration % k == 0;
        } else {
            return false;
        }
    }
}
