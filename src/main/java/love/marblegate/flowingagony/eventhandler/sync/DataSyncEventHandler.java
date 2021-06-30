package love.marblegate.flowingagony.eventhandler.sync;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class DataSyncEventHandler {

    //Sync data when logging
    @SubscribeEvent
    public static void syncDataWhenLogging(PlayerEvent.PlayerLoggedInEvent event) {
        syncAbnormalJoyCapability(event.getPlayer());
    }

    //Sync data when switch dimension
    @SubscribeEvent
    public static void syncDataWhenChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncAbnormalJoyCapability(event.getPlayer());
    }

    //Sync data when respawning
    @SubscribeEvent
    public static void syncDataWhenRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncAbnormalJoyCapability(event.getPlayer());
    }

    private static void syncAbnormalJoyCapability(PlayerEntity player) {
        LazyOptional<IAbnormalJoyCapability> pointCap = player.getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
        pointCap.ifPresent(
                cap-> Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(
                                () -> (ServerPlayerEntity) player
                        ),
                        new AbnormalJoySyncPacket(cap.get()))
        );
    }
}


