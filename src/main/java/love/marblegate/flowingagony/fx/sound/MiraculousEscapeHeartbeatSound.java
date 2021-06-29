package love.marblegate.flowingagony.fx.sound;

import love.marblegate.flowingagony.registry.SoundRegistry;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;

public class MiraculousEscapeHeartbeatSound extends TickableSound {
    private final ClientPlayerEntity player;

    public MiraculousEscapeHeartbeatSound(ClientPlayerEntity player) {
        super(SoundRegistry.miraculous_escape_heartbeat.get(), SoundCategory.PLAYERS);
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 20.0F;
        this.pitch = 1.0F;
    }

    @Override
    public void tick() {
        if (!this.player.isAlive()) {
            this.finishPlaying();
        }
    }
}
