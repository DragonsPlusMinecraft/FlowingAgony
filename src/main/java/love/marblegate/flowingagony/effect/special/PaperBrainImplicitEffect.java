package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class PaperBrainImplicitEffect extends HarmfulBlankImplicitEffect {
    public PaperBrainImplicitEffect() {
        super();
    }


    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.paper_brain_enchantment_active_effect.get()) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC.setDamageBypassesArmor(),entityLivingBaseIn.getRNG().nextInt(4)+5);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 20;
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return false;
        }
    }
}
