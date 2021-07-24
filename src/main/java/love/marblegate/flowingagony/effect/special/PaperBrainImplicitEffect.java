package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class PaperBrainImplicitEffect extends HarmfulBlankImplicitEffect {
    public PaperBrainImplicitEffect() {
        super();
    }


    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.PAPER_BRAIN_ENCHANTMENT_ACTIVE.get()) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC.setDamageBypassesArmor(),entityLivingBaseIn.getRNG().nextInt(4)+5);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 20;
        if (duration > 0) {
            return duration % k == 0;
        }
        return false;
    }
}
