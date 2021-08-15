package love.marblegate.flowingagony.eventhandler;


import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void AbnormalJoyCapEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                LazyOptional<IAbnormalJoyCapability> pointCap = event.getSource().getTrueSource().getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
                pointCap.ifPresent(
                        cap -> {
                            if (cap.get() >= 1) {
                                if (cap.get() >= 5) {
                                    event.getEntityLiving().attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor(), 15);
                                    cap.decrease(5f);
                                } else {
                                    event.getEntityLiving().attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor(), MathHelper.floor(cap.get()) * 2f);
                                    cap.decrease(MathHelper.floor(cap.get()));
                                }
                            }
                            //Sync to Client
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayerEntity) (event.getSource().getTrueSource())
                                    ),
                                    new AbnormalJoySyncPacket(cap.get()));
                        }
                );
            }
        }
    }
}
