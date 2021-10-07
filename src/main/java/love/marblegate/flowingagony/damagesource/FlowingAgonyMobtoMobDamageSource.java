package love.marblegate.flowingagony.damagesource;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nullable;


public class FlowingAgonyMobtoMobDamageSource extends EntityDamageSource {

    public FlowingAgonyMobtoMobDamageSource(String damageTypeIn, LivingEntity damageSourceEntity) {
        super(damageTypeIn, damageSourceEntity);
    }

    @Override
    @Nullable
    public Entity getEntity() {
        return entity;
    }

    /**
     * Gets the death message that is displayed when the player dies
     */
    @Override
    public Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn) {
        String s = "death.attack." + msgId;
        return new TranslatableComponent(s, entityLivingBaseIn.getDisplayName(), entity.getDisplayName());
    }
}
