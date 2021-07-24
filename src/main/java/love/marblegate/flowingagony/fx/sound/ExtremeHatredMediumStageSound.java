package love.marblegate.flowingagony.fx.sound;

import love.marblegate.flowingagony.registry.SoundRegistry;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;

public class ExtremeHatredMediumStageSound extends TickableSound {
    private final ClientPlayerEntity player;

    public ExtremeHatredMediumStageSound(ClientPlayerEntity player) {
        super(SoundRegistry.EXTREME_HATRED_MEDIUM_STAGE_SOUND.get(), SoundCategory.PLAYERS);
        this.player = player;
        repeat = true;
        repeatDelay = 0;
        volume = 35.0F;
        pitch = 1F;
    }

    @Override
    public void tick() {
        if (!player.isAlive()) {
            finishPlaying();
        }
    }
}
