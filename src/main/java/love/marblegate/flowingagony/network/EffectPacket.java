package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.particle.cursedantipathyparticle.CursedAntipathyParticleData;
import love.marblegate.flowingagony.util.client.ClientUtil;
import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EffectPacket {
    private final EffectType type;
    private final double x;
    private final double y;
    private final double z;
    private final double[] args;
    public static IProxy proxy = new IProxy() {};

    public EffectPacket(EffectType type, double x, double y, double z, double... args) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.args = args;
    }

    public EffectPacket(PacketBuffer buffer) {
        this.type = buffer.readEnumValue(EffectType.class);
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        int argsSum = buffer.readInt();
        this.args = new double[argsSum];
        for (int i = 0; i < argsSum; i++) {
            this.args[i] = buffer.readDouble();
        }
    }


    public void toBytes(PacketBuffer buffer) {
        buffer.writeEnumValue(this.type);
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
        buffer.writeInt(this.args.length);
        for (double arg : this.args) {
            buffer.writeDouble(arg);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.safeRunWhenOn(Dist.CLIENT,()-> () -> {
            ctx.get().enqueueWork(() -> {
                proxy = new ClientProxy();
                if(type== EffectType.CURSED_ANTIPATHY_EFFECT){
                    for(int i = 0; i < args[1]; i++) {
                        proxy.addParticleForceNear(new CursedAntipathyParticleData((float) args[0]), x, y, z, 0, 0, 0);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        });
    }

    public enum EffectType {
        CURSED_ANTIPATHY_EFFECT
    }


}
