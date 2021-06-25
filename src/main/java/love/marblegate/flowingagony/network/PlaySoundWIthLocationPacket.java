package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.registry.SoundRegistry;
import love.marblegate.flowingagony.sound.MiraculousEscapeHeartbeatSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlaySoundWIthLocationPacket {
    private final ModSoundType type;
    private final boolean onOrOff;
    private final double x;
    private final double y;
    private final double z;


    public PlaySoundWIthLocationPacket(PacketBuffer buffer) {
        this.type = buffer.readEnumValue(ModSoundType.class);
        this.onOrOff = buffer.readBoolean();
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public PlaySoundWIthLocationPacket(ModSoundType type, boolean onOrOff, double x, double y, double z) {
        this.type = type;
        this.onOrOff = onOrOff;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void toBytes(PacketBuffer buffer) {
        buffer.writeEnumValue(this.type);
        buffer.writeBoolean(this.onOrOff);
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(type== ModSoundType.MALICE_OUTBREAK_KNOCKBACK_SOUND){
                Minecraft.getInstance().world.playSound(x,y,z, SoundRegistry.malice_outbreak_knockback_sound.get(), SoundCategory.PLAYERS,10,1,true);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public enum ModSoundType {
        MALICE_OUTBREAK_KNOCKBACK_SOUND
    }
}
