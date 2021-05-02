package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class PaperBrainImplicitEffect extends ImplicitBaseEffect {
    public PaperBrainImplicitEffect() {
        super(EffectType.HARMFUL, 0);
    }


    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.paper_brain_enchantment_active_effect.get()) {
            int rate = 30;
            if(amplifier==1) rate = 40;
            else if(amplifier==2) rate = 50;
            if(entityLivingBaseIn.getRNG().nextInt(100)<rate){
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC.setDamageBypassesArmor(),1);
            }
        }
    }

    public boolean isReady(int duration, int amplifier) {
        int k = 5;
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return false;
        }
    }
}
