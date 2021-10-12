package love.marblegate.flowingagony.util.proxy;

import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;

public interface IProxy {
    default void addParticleForceNear(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
    }

    default void handleISound(PlaySoundPacket.ModSoundType type, boolean onOrOff) {
    }

    default void playSoundWithLocation(SoundEvent soundEvent, SoundSource category, float volume, float pitch, double x, double y, double z, boolean distanceDelay) {
    }

    default void removeEffect(MobEffect effect) {
    }

}
