package love.marblegate.flowingagony.damagesource;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class FlowingAgonyMobtoMobDamageSource extends DamageSource {
    LivingEntity damageSourceEntity;

    public FlowingAgonyMobtoMobDamageSource(String damageTypeIn, LivingEntity damageSourceEntity) {
        super(damageTypeIn);
        this.damageSourceEntity = damageSourceEntity;
    }

    /**
     * Gets the death message that is displayed when the player dies
     */
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        String s = "death.attack." + this.damageType;
        return new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(),damageSourceEntity.getDisplayName());
    }
}
