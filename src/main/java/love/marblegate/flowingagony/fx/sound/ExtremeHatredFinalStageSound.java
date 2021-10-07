package love.marblegate.flowingagony.fx.sound;

import love.marblegate.flowingagony.fx.SoundRegistry;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;

public class ExtremeHatredFinalStageSound extends AbstractTickableSoundInstance {
    private final LocalPlayer player;

    public ExtremeHatredFinalStageSound(LocalPlayer player) {
        super(SoundRegistry.EXTREME_HATRED_FINAL_STAGE_SOUND.get(), SoundSource.PLAYERS);
        this.player = player;
        looping = true;
        delay = 0;
        volume = 30.0F;
        pitch = 1F;
    }

    @Override
    public void tick() {
        if (!player.isAlive()) {
            stop();
        }
    }
}
