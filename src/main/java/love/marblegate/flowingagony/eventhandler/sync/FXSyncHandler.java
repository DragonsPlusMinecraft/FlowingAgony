package love.marblegate.flowingagony.eventhandler.sync;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class FXSyncHandler {

    //Fix FX disappearing when switch dimension
    @SubscribeEvent
    public static void fixFXWhenChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        reapplyFX(event.getPlayer());
    }

    //Fix FX disappearing when logging
    @SubscribeEvent
    public static void fixFXWhenLogging(PlayerEvent.PlayerLoggedInEvent event) {
        reapplyFX(event.getPlayer());
    }

    private static void reapplyFX(Player player) {
        if (!player.level.isClientSide()) {
            if (player.hasEffect(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get())) {
                Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(
                                () -> (ServerPlayer) player
                        ),
                        new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT, true));
            }
            if (player.hasEffect(EffectRegistry.EXTREME_HATRED.get())) {
                int temp = player.getEffect(EffectRegistry.EXTREME_HATRED.get()).getAmplifier();
                switch (temp) {
                    case 0:
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) player
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE, true));
                        break;
                    case 1:
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) player
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE, true));
                        break;
                    case 2:
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayer) player
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE, true));
                        break;
                    default:
                }
            }
        }
    }
}


