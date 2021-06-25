package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.network.EffectPacket;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.network.PacketDistributor;

public class CursedAntipathyEffect extends Effect {
    public CursedAntipathyEffect() {
        super(EffectType.HARMFUL, 18432);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.cursed_antipathy_effect.get()) {
            entityLivingBaseIn.attackEntityFrom((new DamageSource("flowingagony.cursed_antipathy_effect")).setDamageBypassesArmor(), 1.0F);
            //Play Particle Effect
            if (!entityLivingBaseIn.world.isRemote) {
                Networking.INSTANCE.send(
                        PacketDistributor.NEAR.with(
                                () -> new PacketDistributor.TargetPoint(entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY(),entityLivingBaseIn.getPosZ(),
                                        192,entityLivingBaseIn.world.getDimensionKey())
                        ),
                        new EffectPacket(EffectPacket.EffectType.CURSED_ANTIPATHY_EFFECT,entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY()+1,entityLivingBaseIn.getPosZ(),
                                (amplifier+1)*0.5,(amplifier+1)*2));
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 40;
        if(amplifier>0){
            k /= (amplifier * amplifier);
        }
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return false;
        }
    }
}
