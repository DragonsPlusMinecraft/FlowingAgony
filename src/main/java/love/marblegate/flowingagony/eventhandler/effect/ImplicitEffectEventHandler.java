package love.marblegate.flowingagony.eventhandler.effect;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.packet.PlaySoundPacket;
import love.marblegate.flowingagony.effect.EffectRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class ImplicitEffectEventHandler {
    @SubscribeEvent
    public static void doHatredBloodlineEnchantmentActiveEffectEvent(LivingHurtEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                if (((Player) event.getSource().getEntity()).hasEffect(EffectRegistry.HATRED_BLOODLINE_ENCHANTMENT_ACTIVE.get())) {
                    int effectLvl = ((Player) event.getSource().getEntity()).getEffect(EffectRegistry.HATRED_BLOODLINE_ENCHANTMENT_ACTIVE.get()).getAmplifier() + 1;
                    event.setAmount((float) (event.getAmount() * (1 + effectLvl * 0.25)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void doCleanMiraculousEscapeSoundFX(PotionEvent.PotionExpiryEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getEntityLiving() instanceof Player) {
                if (event.getPotionEffect().getEffect().equals(EffectRegistry.MIRACULOUS_ESCAPE_ENCHANTMENT_ACTIVE.get())) {
                    //Play Sound Effect
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayer) event.getEntityLiving()
                            ),
                            new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT, false));
                }
            }
        }
    }
}
