package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveEffectSyncToClientPacket {
    private final MobEffect effect;
    public static IProxy proxy = new IProxy() {
    };

    public RemoveEffectSyncToClientPacket(MobEffect effect) {
        this.effect = effect;
    }

    public RemoveEffectSyncToClientPacket(FriendlyByteBuf buffer) {
        effect = buffer.readRegistryIdSafe(MobEffect.class);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeRegistryId(effect);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ctx.get().enqueueWork(() -> {
                proxy = new ClientProxy();
                proxy.removeEffect(effect);
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
