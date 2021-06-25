package love.marblegate.flowingagony.network;

import love.marblegate.flowingagony.particle.cursedantipathyparticle.CursedAntipathyParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EffectPacket {
    private final EffectType type;
    private final double x;
    private final double y;
    private final double z;
    private final double[] args;

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
        ctx.get().enqueueWork(() -> {
            if(type== EffectType.CURSED_ANTIPATHY_EFFECT){
                addParticleForceNear(Minecraft.getInstance().world,new CursedAntipathyParticleData((float) args[0]),x,y,z,0,0,0);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public enum EffectType {
        CURSED_ANTIPATHY_EFFECT
    }

    public void addParticleForce(World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(particleData, true, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    public void addParticleForceNear(World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        ActiveRenderInfo info = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        if (info.isValid() && info.getProjectedView().squareDistanceTo(x, y, z) <= 512.0D) {
            for(int i = 0; i < args[1]; i++){
                addParticleForce(world, particleData, x, y, z, xSpeed, ySpeed, zSpeed);
            }
        }
    }
}
