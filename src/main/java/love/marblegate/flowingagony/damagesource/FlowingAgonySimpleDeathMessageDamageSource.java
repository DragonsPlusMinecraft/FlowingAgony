package love.marblegate.flowingagony.damagesource;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FlowingAgonySimpleDeathMessageDamageSource extends DamageSource {
    public FlowingAgonySimpleDeathMessageDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        String s = "death.attack." + damageType;
        return new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName());
    }
}
