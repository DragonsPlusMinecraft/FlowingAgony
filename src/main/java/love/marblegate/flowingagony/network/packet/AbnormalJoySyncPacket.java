package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.ModCapManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class AbnormalJoySyncPacket {
    private final float value;

    public AbnormalJoySyncPacket(float value) {
        this.value = value;
    }

    public AbnormalJoySyncPacket(FriendlyByteBuf buffer) {
        value = buffer.readFloat();
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeFloat(value);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ctx.get().enqueueWork(() -> {
                LazyOptional<AbnormalJoyCapability> pointCap = Minecraft.getInstance().player.getCapability(ModCapManager.ABNORMALJOY_CAPABILITY);
                pointCap.ifPresent(
                        cap -> cap.set(value)
                );
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
