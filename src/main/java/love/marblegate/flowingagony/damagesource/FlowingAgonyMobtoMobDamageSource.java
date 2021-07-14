package love.marblegate.flowingagony.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;


public class FlowingAgonyMobtoMobDamageSource extends DamageSource {
    final LivingEntity damageSourceEntity;

    public FlowingAgonyMobtoMobDamageSource(String damageTypeIn, LivingEntity damageSourceEntity) {
        super(damageTypeIn);
        this.damageSourceEntity = damageSourceEntity;
    }

    @Override
    @Nullable
    public Entity getTrueSource() {
        return damageSourceEntity;
    }

    /**
     * Gets the death message that is displayed when the player dies
     */
    @Override
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        String s = "death.attack." + damageType;
        return new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(),damageSourceEntity.getDisplayName());
    }
}
