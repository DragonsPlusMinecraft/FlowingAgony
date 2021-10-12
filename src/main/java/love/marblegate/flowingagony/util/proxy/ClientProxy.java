package love.marblegate.flowingagony.util.proxy;

import love.marblegate.flowingagony.fx.SoundRegistry;
import love.marblegate.flowingagony.fx.sound.ExtremeHatredFinalStageSound;
import love.marblegate.flowingagony.fx.sound.ExtremeHatredFirstStageSound;
import love.marblegate.flowingagony.fx.sound.ExtremeHatredMediumStageSound;
import love.marblegate.flowingagony.fx.sound.MiraculousEscapeHeartbeatSound;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;

public class ClientProxy implements IProxy {
    @Override
    public void addParticleForceNear(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        Level world = Minecraft.getInstance().level;
        Camera info = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (info.isInitialized() && info.getPosition().distanceToSqr(x, y, z) <= 512.0D) {
            world.addParticle(particleData, true, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    public void handleISound(PlaySoundPacket.ModSoundType type, boolean onOrOff) {
        if (type == PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT) {
            if (onOrOff) {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.MIRACULOUS_ESCAPE_HEARTBEAT.getId(), SoundSource.PLAYERS);
                Minecraft.getInstance().getSoundManager().play(new MiraculousEscapeHeartbeatSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.MIRACULOUS_ESCAPE_HEARTBEAT.getId(), SoundSource.PLAYERS);
            }

        }
        if (type == PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE) {
            if (onOrOff) {
                Minecraft.getInstance().getSoundManager().play(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.EXTREME_HATRED_FIRST_STAGE_SOUND.getId(), SoundSource.PLAYERS);
            }

        }
        if (type == PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE) {
            if (onOrOff) {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.EXTREME_HATRED_FIRST_STAGE_SOUND.getId(), SoundSource.PLAYERS);
                Minecraft.getInstance().getSoundManager().play(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.EXTREME_HATRED_MEDIUM_STAGE_SOUND.getId(), SoundSource.PLAYERS);
            }

        }
        if (type == PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE) {
            if (onOrOff) {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.EXTREME_HATRED_MEDIUM_STAGE_SOUND.getId(), SoundSource.PLAYERS);
                Minecraft.getInstance().getSoundManager().play(new ExtremeHatredFinalStageSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundManager().stop(SoundRegistry.EXTREME_HATRED_FINAL_STAGE_SOUND.getId(), SoundSource.PLAYERS);
            }
        }
    }

    @Override
    public void playSoundWithLocation(SoundEvent soundEvent, SoundSource category, float volume, float pitch, double x, double y, double z, boolean distanceDelay) {
        Minecraft.getInstance().level.playLocalSound(x, y, z, soundEvent, category, volume, pitch, distanceDelay);
    }

    @Override
    public void removeEffect(MobEffect effect) {
        Minecraft.getInstance().player.removeEffectNoUpdate(effect);
    }
}
