package love.marblegate.flowingagony.util.proxy;

import love.marblegate.flowingagony.network.PlaySoundPacket;
import love.marblegate.flowingagony.registry.SoundRegistry;
import love.marblegate.flowingagony.sound.ExtremeHatredFinalStageSound;
import love.marblegate.flowingagony.sound.ExtremeHatredFirstStageSound;
import love.marblegate.flowingagony.sound.ExtremeHatredMediumStageSound;
import love.marblegate.flowingagony.sound.MiraculousEscapeHeartbeatSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ClientProxy implements IProxy{
    @Override
    public void addParticleForceNear(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        World world = Minecraft.getInstance().world;
        ActiveRenderInfo info = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        if (info.isValid() && info.getProjectedView().squareDistanceTo(x, y, z) <= 512.0D) {
            world.addParticle(particleData, true, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    public void handleISound(PlaySoundPacket.ModSoundType type, boolean onOrOff) {
        if(type== PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT){
            if(onOrOff){
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.miraculous_escape_heartbeat.getId(), SoundCategory.PLAYERS);
                Minecraft.getInstance().getSoundHandler().play(new MiraculousEscapeHeartbeatSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.miraculous_escape_heartbeat.getId(), SoundCategory.PLAYERS);
            }

        }
        if(type== PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE){
            if(onOrOff){
                Minecraft.getInstance().getSoundHandler().play(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.extreme_hatred_first_stage_sound.getId(), SoundCategory.PLAYERS);
            }

        }
        if(type== PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE){
            if(onOrOff){
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.extreme_hatred_first_stage_sound.getId(), SoundCategory.PLAYERS);
                Minecraft.getInstance().getSoundHandler().play(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player));
            } else {
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.extreme_hatred_medium_stage_sound.getId(), SoundCategory.PLAYERS);
            }

        }
        if(type== PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE){
            if(onOrOff) {
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.extreme_hatred_medium_stage_sound.getId(), SoundCategory.PLAYERS);
                Minecraft.getInstance().getSoundHandler().play(new ExtremeHatredFinalStageSound(Minecraft.getInstance().player));
            }
            else{
                Minecraft.getInstance().getSoundHandler().stop(SoundRegistry.extreme_hatred_final_stage_sound.getId(), SoundCategory.PLAYERS);
            }
        }
    }

    @Override
    public void playSoundWithLocation(SoundEvent soundEvent, SoundCategory category, float volume, float pitch, double x, double y, double z, boolean distanceDelay) {
        Minecraft.getInstance().world.playSound(x, y, z, soundEvent, category, volume, pitch, distanceDelay);
    }
}
