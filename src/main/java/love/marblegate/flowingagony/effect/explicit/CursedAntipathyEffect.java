package love.marblegate.flowingagony.effect.explicit;

import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.ParticleEffectPacket;
import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class CursedAntipathyEffect extends MobEffect {
    public CursedAntipathyEffect() {
        super(MobEffectCategory.HARMFUL, 18432);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.CURSED_ANTIPATHY.get()) {
            entityLivingBaseIn.hurt(CustomDamageSource.CURSED_ANTIPATHY, 1.0F);
            //Play Particle Effect
            if (!entityLivingBaseIn.level.isClientSide) {
                Networking.INSTANCE.send(
                        PacketDistributor.NEAR.with(
                                () -> new PacketDistributor.TargetPoint(entityLivingBaseIn.getX(), entityLivingBaseIn.getY(), entityLivingBaseIn.getZ(),
                                        192, entityLivingBaseIn.level.dimension())
                        ),
                        new ParticleEffectPacket(ParticleEffectPacket.EffectType.CURSED_ANTIPATHY_EFFECT, entityLivingBaseIn.getX(), entityLivingBaseIn.getY() + 1, entityLivingBaseIn.getZ(),
                                (amplifier + 1) * 0.5, (amplifier + 1) * 2));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
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
