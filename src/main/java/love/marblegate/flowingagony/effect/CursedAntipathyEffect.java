package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.packet.ParticleEffectPacket;
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
            entityLivingBaseIn.attackEntityFrom(CustomDamageSource.CURSED_ANTIPATHY, 1.0F);
            //Play Particle Effect
            if (!entityLivingBaseIn.world.isRemote) {
                Networking.INSTANCE.send(
                        PacketDistributor.NEAR.with(
                                () -> new PacketDistributor.TargetPoint(entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY(),entityLivingBaseIn.getPosZ(),
                                        192,entityLivingBaseIn.world.getDimensionKey())
                        ),
                        new ParticleEffectPacket(ParticleEffectPacket.EffectType.CURSED_ANTIPATHY_EFFECT,entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY()+1,entityLivingBaseIn.getPosZ(),
                                (amplifier+1)*0.5,(amplifier+1)*2));
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k;
        switch (amplifier) {
            case 0:
                k = 100;
                break;
            case 1:
                k = 80;
                break;
            case 2:
                k = 60;
                break;
            case 3:
                k = 40;
                break;
            default:
                k = 30;
        }
        return duration % k == 0;
    }
}
