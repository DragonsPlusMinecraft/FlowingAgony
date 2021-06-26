package love.marblegate.flowingagony.sound;

import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;

public class ExtremeHatredFirstStageSound extends TickableSound {
    private final ClientPlayerEntity player;

    public ExtremeHatredFirstStageSound(ClientPlayerEntity player) {
        super(SoundRegistry.extreme_hatred_first_stage_sound.get(), SoundCategory.PLAYERS);
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 18.0F;
        this.pitch = 2F;
        this.isGlobal();
    }

    @Override
    public void tick() {
        if (!this.player.isAlive()) {
            this.finishPlaying();
        }
    }
}
