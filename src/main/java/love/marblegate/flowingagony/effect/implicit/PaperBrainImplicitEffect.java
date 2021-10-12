package love.marblegate.flowingagony.effect.implicit;

import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class PaperBrainImplicitEffect extends HarmfulBlankImplicitEffect {
    public PaperBrainImplicitEffect() {
        super();
    }


    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.PAPER_BRAIN_ENCHANTMENT_ACTIVE.get()) {
            entityLivingBaseIn.hurt(DamageSource.MAGIC.bypassArmor(), entityLivingBaseIn.getRandom().nextInt(4) + 5);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 20;
        if (duration > 0) {
            return duration % k == 0;
        }
        return false;
    }
}
