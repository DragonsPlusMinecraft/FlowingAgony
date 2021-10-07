package love.marblegate.flowingagony.damagesource;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class FlowingAgonySimpleDeathMessageDamageSource extends DamageSource {
    public FlowingAgonySimpleDeathMessageDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn) {
        String s = "death.attack." + msgId;
        return new TranslatableComponent(s, entityLivingBaseIn.getDisplayName());
    }
}
