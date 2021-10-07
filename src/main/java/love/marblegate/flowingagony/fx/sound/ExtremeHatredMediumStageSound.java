package love.marblegate.flowingagony.fx.sound;

import love.marblegate.flowingagony.fx.SoundRegistry;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;

public class ExtremeHatredMediumStageSound extends AbstractTickableSoundInstance {
    private final LocalPlayer player;

    public ExtremeHatredMediumStageSound(LocalPlayer player) {
        super(SoundRegistry.EXTREME_HATRED_MEDIUM_STAGE_SOUND.get(), SoundSource.PLAYERS);
        this.player = player;
        looping = true;
        delay = 0;
        volume = 35.0F;
        pitch = 1F;
    }

    @Override
    public void tick() {
        if (!player.isAlive()) {
            stop();
        }
    }
}
