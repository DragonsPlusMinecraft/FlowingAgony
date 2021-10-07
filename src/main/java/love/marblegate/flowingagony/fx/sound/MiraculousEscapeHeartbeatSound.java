package love.marblegate.flowingagony.fx.sound;

import love.marblegate.flowingagony.fx.SoundRegistry;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;

public class MiraculousEscapeHeartbeatSound extends AbstractTickableSoundInstance {
    private final LocalPlayer player;

    public MiraculousEscapeHeartbeatSound(LocalPlayer player) {
        super(SoundRegistry.MIRACULOUS_ESCAPE_HEARTBEAT.get(), SoundSource.PLAYERS);
        this.player = player;
        looping = true;
        delay = 0;
        volume = 20.0F;
        pitch = 1.0F;
    }

    @Override
    public void tick() {
        if (!player.isAlive()) {
            stop();
        }
    }
}
