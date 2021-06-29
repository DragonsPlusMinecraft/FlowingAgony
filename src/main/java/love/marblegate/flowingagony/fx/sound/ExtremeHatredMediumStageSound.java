package love.marblegate.flowingagony.fx.sound;

import love.marblegate.flowingagony.registry.SoundRegistry;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;

public class ExtremeHatredMediumStageSound extends TickableSound {
    private final ClientPlayerEntity player;

    public ExtremeHatredMediumStageSound(ClientPlayerEntity player) {
        super(SoundRegistry.extreme_hatred_medium_stage_sound.get(), SoundCategory.PLAYERS);
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 35.0F;
        this.pitch = 1F;
    }

    @Override
    public void tick() {
        if (!this.player.isAlive()) {
            this.finishPlaying();
        }
    }
}
