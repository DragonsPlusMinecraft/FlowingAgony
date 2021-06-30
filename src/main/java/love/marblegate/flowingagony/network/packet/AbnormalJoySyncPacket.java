package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.fx.particle.cursedantipathyparticle.CursedAntipathyParticleData;
import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AbnormalJoySyncPacket {
    private final float value;

    public AbnormalJoySyncPacket(float value) {
        this.value = value;
    }

    public AbnormalJoySyncPacket(PacketBuffer buffer) {
        this.value = buffer.readFloat();
    }


    public void toBytes(PacketBuffer buffer) {
        buffer.writeFloat(this.value);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()-> () -> {
            ctx.get().enqueueWork(() -> {
                LazyOptional<IAbnormalJoyCapability> pointCap = Minecraft.getInstance().player.getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
                pointCap.ifPresent(
                        cap-> cap.set(value)
                );
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
