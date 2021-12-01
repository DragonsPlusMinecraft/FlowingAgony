package love.marblegate.flowingagony.eventhandler;


import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.ModCapManager;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void AbnormalJoyCapEvent(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                LazyOptional<AbnormalJoyCapability> pointCap = event.getSource().getEntity().getCapability(ModCapManager.ABNORMALJOY_CAPABILITY);
                pointCap.ifPresent(
                        cap -> {
                            if (cap.get() >= 1) {
                                if (cap.get() >= 5) {
                                    event.getEntityLiving().hurt(DamageSource.GENERIC.bypassArmor(), 15);
                                    cap.decrease(5f);
                                } else {
                                    event.getEntityLiving().hurt(DamageSource.GENERIC.bypassArmor(), Mth.floor(cap.get()) * 2f);
                                    cap.decrease(Mth.floor(cap.get()));
                                }
                            }
                            //Sync to Client
                            Networking.INSTANCE.send(
                                    PacketDistributor.PLAYER.with(
                                            () -> (ServerPlayer) (event.getSource().getEntity())
                                    ),
                                    new AbnormalJoySyncPacket(cap.get()));
                        }
                );
            }
        }
    }
}
