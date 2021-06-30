package love.marblegate.flowingagony.util.proxy;

import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public interface IProxy {
    default void addParticleForceNear(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){}

    default void handleISound(PlaySoundPacket.ModSoundType type, boolean onOrOff){}

    default void playSoundWithLocation(SoundEvent soundEvent, SoundCategory category, float volume, float pitch, double x, double y, double z, boolean distanceDelay){}

    default void removeEffect(Effect effect){}

}
