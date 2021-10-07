package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PlaySoundPacket {
    private final ModSoundType type;
    private final boolean onOrOff;
    public static IProxy proxy = new IProxy() {
    };

    public PlaySoundPacket(FriendlyByteBuf buffer) {
        type = buffer.readEnum(ModSoundType.class);
        onOrOff = buffer.readBoolean();
    }

    public PlaySoundPacket(ModSoundType type, boolean onOrOff) {
        this.type = type;
        this.onOrOff = onOrOff;
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeEnum(type);
        buffer.writeBoolean(onOrOff);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            proxy = new ClientProxy();
            ctx.get().enqueueWork(() -> proxy.handleISound(type, onOrOff));
            ctx.get().setPacketHandled(true);
        });

    }

    public enum ModSoundType {
        MIRACULOUS_ESCAPE_HEARTBEAT,
        EXTREME_HATRED_FIRST_STAGE,
        EXTREME_HATRED_MEDIUM_STAGE,
        EXTREME_HATRED_FINAL_STAGE
    }
}
