package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.registry.SoundRegistry;
import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlaySoundWIthLocationPacket {
    private final ModSoundType type;
    private final boolean onOrOff;
    private final double x;
    private final double y;
    private final double z;
    public static IProxy proxy = new IProxy() {};


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
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()-> () -> {
            proxy = new ClientProxy();
            ctx.get().enqueueWork(() -> {
                if (type == ModSoundType.MALICE_OUTBREAK_KNOCKBACK_SOUND) {
                    proxy.playSoundWithLocation(SoundRegistry.malice_outbreak_knockback_sound.get(), SoundCategory.PLAYERS, 5, 0.5F,x,y,z,true);
                }
            });
            ctx.get().setPacketHandled(true);
        });
    }

    public enum ModSoundType {
        MALICE_OUTBREAK_KNOCKBACK_SOUND
    }
}
