package love.marblegate.flowingagony.network.packet;

import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.util.proxy.ClientProxy;
import love.marblegate.flowingagony.util.proxy.IProxy;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveEffectSyncToClientPacket {
    private final EffectType effectType;
    public static IProxy proxy = new IProxy() {};

    public RemoveEffectSyncToClientPacket(EffectType effectType) {
        this.effectType = effectType;
    }

    public RemoveEffectSyncToClientPacket(PacketBuffer buffer) {
        this.effectType = buffer.readEnumValue(EffectType.class);
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeEnumValue(this.effectType);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()-> () -> {
            ctx.get().enqueueWork(() -> {
                proxy = new ClientProxy();
                proxy.removeEffect(effectType.getEffect());
            });
            ctx.get().setPacketHandled(true);
        });
    }

    public enum EffectType {
        HUNGER(Effects.HUNGER),
        POISON(Effects.POISON),
        LIGHTBURN_FUNGAL_INFECTION(EffectRegistry.lightburn_fungal_infection_effect.get()),
        BLINDNESS(Effects.BLINDNESS);

        final Effect effect;

        EffectType(Effect effect) {
            this.effect = effect;
        }

        public Effect getEffect() {
            return effect;
        }
    }
}
