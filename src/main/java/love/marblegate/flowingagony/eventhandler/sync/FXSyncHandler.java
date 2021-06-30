package love.marblegate.flowingagony.eventhandler.sync;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

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

    private static void reapplyFX(PlayerEntity player) {
        if (!player.world.isRemote()) {
            if (player.isPotionActive(EffectRegistry.miraculous_escape_enchantment_active_effect.get())) {
                Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(
                                () -> (ServerPlayerEntity) player
                        ),
                        new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT, true));
            }
            if (player.isPotionActive(EffectRegistry.extreme_hatred_effect.get())) {
                int temp = player.getActivePotionEffect(EffectRegistry.extreme_hatred_effect.get()).getAmplifier();
                switch (temp) {
                    case 0:
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) player
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FIRST_STAGE, true));
                        break;
                    case 1:
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) player
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_MEDIUM_STAGE, true));
                        break;
                    case 2:
                        Networking.INSTANCE.send(
                                PacketDistributor.PLAYER.with(
                                        () -> (ServerPlayerEntity) player
                                ),
                                new PlaySoundPacket(PlaySoundPacket.ModSoundType.EXTREME_HATRED_FINAL_STAGE, true));
                        break;
                    default:
                }
            }
        }
    }
}


