package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.fx.particle.cursedantipathyparticle.CursedAntipathyParticleData;
import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticleEffectPacket {
    private final EffectType type;
    private final double x;
    private final double y;
    private final double z;
    private final double[] args;
    public static IProxy proxy = new IProxy() {
    };

    public ParticleEffectPacket(EffectType type, double x, double y, double z, double... args) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.args = args;
    }

    public ParticleEffectPacket(FriendlyByteBuf buffer) {
        type = buffer.readEnum(EffectType.class);
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        int argsSum = buffer.readInt();
        args = new double[argsSum];
        for (int i = 0; i < argsSum; i++) {
            args[i] = buffer.readDouble();
        }
    }


    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeEnum(type);
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeInt(args.length);
        for (double arg : args) {
            buffer.writeDouble(arg);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ctx.get().enqueueWork(() -> {
                proxy = new ClientProxy();
                if (type == EffectType.CURSED_ANTIPATHY_EFFECT) {
                    for (int i = 0; i < args[1]; i++) {
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
