package love.marblegate.flowingagony.sound;

import love.marblegate.flowingagony.registry.SoundRegistry;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;

public class ExtremeHatredFinalStageSound extends TickableSound {
    private final ClientPlayerEntity player;

    public ExtremeHatredFinalStageSound(ClientPlayerEntity player) {
        super(SoundRegistry.extreme_hatred_final_stage_sound.get(), SoundCategory.PLAYERS);
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 12.0F;
        this.isGlobal();
    }

    @Override
    public void tick() {
        if (!this.player.isAlive()) {
            this.finishPlaying();
        }
    }
}
