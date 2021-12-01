package love.marblegate.flowingagony.eventhandler.sync;

import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.ModCapManager;
import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.AbnormalJoySyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

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

    private static void syncAbnormalJoyCapability(Player player) {
        LazyOptional<AbnormalJoyCapability> pointCap = player.getCapability(ModCapManager.ABNORMALJOY_CAPABILITY);
        pointCap.ifPresent(
                cap -> Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(
                                () -> (ServerPlayer) player
                        ),
                        new AbnormalJoySyncPacket(cap.get()))
        );
    }
}


