package love.marblegate.flowingagony.capibility;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class ModCapManager {
    // Holding the Capability Instances
    public static Capability<AbnormalJoyCapability> ABNORMALJOY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static Capability<CoolDown> COOL_DOWN_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static Capability<HatredBloodlineStatusCapability> HATRED_BLOODLINE_STATUS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static Capability<LastSweetDreamCapability> LAST_SWEET_DREAM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    // Attaching Capability to Player
    @SubscribeEvent
    public static void attachCap(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player) {
            PlayerCapabilityProvider provider = new PlayerCapabilityProvider();
            event.addCapability(new ResourceLocation("flowingagony", "player_capability"), provider);
            event.addListener(provider::invalidate);
        }
    }

    //Manager Capability Data Transferring Event
    @SubscribeEvent
    public static void migrateCapDataWhenPlayerRespawn(PlayerEvent.Clone event) {
        if (!event.getPlayer().level.isClientSide()) {
            LazyOptional<HatredBloodlineStatusCapability> oldHatredBloodStatus = event.getOriginal().getCapability(ModCapManager.HATRED_BLOODLINE_STATUS_CAPABILITY);
            LazyOptional<HatredBloodlineStatusCapability> newHatredBloodStatus = event.getPlayer().getCapability(ModCapManager.HATRED_BLOODLINE_STATUS_CAPABILITY);
            if (oldHatredBloodStatus.isPresent() && newHatredBloodStatus.isPresent()) {
                newHatredBloodStatus.ifPresent((newCap) -> oldHatredBloodStatus.ifPresent((oldCap) -> newCap.setActiveLevel(oldCap.getActiveLevel())));
            }
            LazyOptional<AbnormalJoyCapability> oldAbnormalJoyPoint = event.getOriginal().getCapability(ModCapManager.ABNORMALJOY_CAPABILITY);
            LazyOptional<AbnormalJoyCapability> newAbnormalJoyPoint = event.getPlayer().getCapability(ModCapManager.ABNORMALJOY_CAPABILITY);
            if (oldAbnormalJoyPoint.isPresent() && newAbnormalJoyPoint.isPresent()) {
                newAbnormalJoyPoint.ifPresent((newCap) -> oldAbnormalJoyPoint.ifPresent((oldCap) -> newCap.set(oldCap.get())));
            }
            LazyOptional<CoolDown> oldCoolDown = event.getOriginal().getCapability(ModCapManager.COOL_DOWN_CAPABILITY);
            LazyOptional<CoolDown> newCoolDown = event.getPlayer().getCapability(ModCapManager.COOL_DOWN_CAPABILITY);
            if (oldCoolDown.isPresent() && newCoolDown.isPresent()) {
                newCoolDown.ifPresent((newCap) -> oldCoolDown.ifPresent((oldCap) -> newCap.setMap(oldCap.getMap())));
            }
            LazyOptional<LastSweetDreamCapability> oldLastSweetDreamItem = event.getOriginal().getCapability(ModCapManager.LAST_SWEET_DREAM_CAPABILITY);
            LazyOptional<LastSweetDreamCapability> newLastSweetDreamItem = event.getPlayer().getCapability(ModCapManager.LAST_SWEET_DREAM_CAPABILITY);
            if (oldLastSweetDreamItem.isPresent() && newLastSweetDreamItem.isPresent()) {
                newLastSweetDreamItem.ifPresent((newCap) -> oldLastSweetDreamItem.ifPresent((oldCap) -> newCap.saveItemStack(oldCap.getItemStack())));
            }
        }
    }
}
