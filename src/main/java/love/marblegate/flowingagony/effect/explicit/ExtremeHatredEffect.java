package love.marblegate.flowingagony.effect.explicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ExtremeHatredEffect extends MobEffect {
    public ExtremeHatredEffect() {
        super(MobEffectCategory.HARMFUL, 16711680);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.EXTREME_HATRED.get()) {
            if (entityLivingBaseIn instanceof Player) {
                crazilyComsumeFoodLevel((Player) entityLivingBaseIn);
            }
        }
    }

    static void crazilyComsumeFoodLevel(Player player) {
        player.getFoodData().addExhaustion(4);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = (15 / (amplifier + 1));
        if (k > 0) {
            return duration % k == 0;
        } else {
            return false;
        }
    }
}
