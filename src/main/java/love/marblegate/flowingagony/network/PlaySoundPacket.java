package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.sound.ExtremeHatredFinalStageSound;
import love.marblegate.flowingagony.sound.ExtremeHatredFirstStageSound;
import love.marblegate.flowingagony.sound.ExtremeHatredMediumStageSound;
import love.marblegate.flowingagony.sound.MiraculousEscapeHeartbeatSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlaySoundPacket {
    private final ModSoundType type;
    private final boolean onOrOff;

    public PlaySoundPacket(PacketBuffer buffer) {
        this.type = buffer.readEnumValue(ModSoundType.class);
        this.onOrOff = buffer.readBoolean();
    }

    public PlaySoundPacket(ModSoundType type, boolean onOrOff) {
        this.type = type;
        this.onOrOff = onOrOff;
    }


    public void toBytes(PacketBuffer buffer) {
        buffer.writeEnumValue(this.type);
        buffer.writeBoolean(this.onOrOff);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(type==ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT){
                Minecraft.getInstance().getSoundHandler().play(new MiraculousEscapeHeartbeatSound(Minecraft.getInstance().player));
            }
            if(type==ModSoundType.EXTREME_HATRED_FIRST_STAGE){
                if(this.onOrOff){
                    Minecraft.getInstance().getSoundHandler().play(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player));
                } else {
                    if(Minecraft.getInstance().getSoundHandler().isPlaying(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player))){
                        Minecraft.getInstance().getSoundHandler().stop(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player));
                    }
                }

            }
            if(type==ModSoundType.EXTREME_HATRED_MEDIUM_STAGE){
                if(this.onOrOff){
                    if(Minecraft.getInstance().getSoundHandler().isPlaying(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player))){
                        Minecraft.getInstance().getSoundHandler().stop(new ExtremeHatredFirstStageSound(Minecraft.getInstance().player));
                    }
                    Minecraft.getInstance().getSoundHandler().play(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player));
                } else {
                    if(Minecraft.getInstance().getSoundHandler().isPlaying(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player))){
                        Minecraft.getInstance().getSoundHandler().stop(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player));
                    }
                }

            }
            if(type==ModSoundType.EXTREME_HATRED_FINAL_STAGE){
                if(this.onOrOff) {
                    if(Minecraft.getInstance().getSoundHandler().isPlaying(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player))){
                        Minecraft.getInstance().getSoundHandler().stop(new ExtremeHatredMediumStageSound(Minecraft.getInstance().player));
                    }
                    Minecraft.getInstance().getSoundHandler().play(new ExtremeHatredFinalStageSound(Minecraft.getInstance().player));
                }
                else{
                    if(Minecraft.getInstance().getSoundHandler().isPlaying(new ExtremeHatredFinalStageSound(Minecraft.getInstance().player))){
                        Minecraft.getInstance().getSoundHandler().stop(new ExtremeHatredFinalStageSound(Minecraft.getInstance().player));
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public enum ModSoundType {
        MIRACULOUS_ESCAPE_HEARTBEAT,
        EXTREME_HATRED_FIRST_STAGE,
        EXTREME_HATRED_MEDIUM_STAGE,
        EXTREME_HATRED_FINAL_STAGE
    }
}
