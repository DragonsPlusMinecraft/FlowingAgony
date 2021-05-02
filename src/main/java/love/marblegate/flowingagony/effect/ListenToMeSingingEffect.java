package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class ListenToMeSingingEffect extends Effect {
    public ListenToMeSingingEffect() {
        super(EffectType.HARMFUL, 6881280);
    }

    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.listen_to_me_singing_effect.get()) {
            int duration = entityLivingBaseIn.getActivePotionEffect(this.getEffect()).getDuration();
            if(duration%40>25){
                entityLivingBaseIn.setMotion(0,0.41,0);
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
            else if(duration%40>7){
                entityLivingBaseIn.setMotion(0,0.0,0);
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
            else if(duration%40>0){
                entityLivingBaseIn.setMotion(0,-0.79,0);
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
            else if(duration%40==0){
                entityLivingBaseIn.setMotion(0,0,0);
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
                entityLivingBaseIn.attackEntityFrom((new DamageSource("flowingagony.rhythm_of_universe")).setDamageBypassesArmor(),
                        entityLivingBaseIn.getMaxHealth()*0.1f+entityLivingBaseIn.getHealth()*0.4f);
            }
        }
    }

    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
