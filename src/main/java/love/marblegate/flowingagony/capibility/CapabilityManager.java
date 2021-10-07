package love.marblegate.flowingagony.capibility;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CapabilityManager {
    // Holding the Capability Instances
    @CapabilityInject(AbnormalJoyCapability.class)
    public static Capability<AbnormalJoyCapability> ABNORMALJOY_CAPABILITY = null;
    @CapabilityInject(CoolDown.class)
    public static Capability<CoolDown> COOL_DOWN_CAPABILITY = null;
    @CapabilityInject(HatredBloodlineStatusCapability.class)
    public static Capability<HatredBloodlineStatusCapability> HATRED_BLOODLINE_STATUS_CAPABILITY = null;
    @CapabilityInject(LastSweetDreamCapability.class)
    public static Capability<LastSweetDreamCapability> LAST_SWEET_DREAM_CAPABILITY = null;

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
            LazyOptional<HatredBloodlineStatusCapability> oldHatredBloodStatus = event.getOriginal().getCapability(CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY);
            LazyOptional<HatredBloodlineStatusCapability> newHatredBloodStatus = event.getPlayer().getCapability(CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY);
            if (oldHatredBloodStatus.isPresent() && newHatredBloodStatus.isPresent()) {
                newHatredBloodStatus.ifPresent((newCap) -> oldHatredBloodStatus.ifPresent((oldCap) -> newCap.setActiveLevel(oldCap.getActiveLevel())));
            }
            LazyOptional<AbnormalJoyCapability> oldAbnormalJoyPoint = event.getOriginal().getCapability(CapabilityManager.ABNORMALJOY_CAPABILITY);
            LazyOptional<AbnormalJoyCapability> newAbnormalJoyPoint = event.getPlayer().getCapability(CapabilityManager.ABNORMALJOY_CAPABILITY);
            if (oldAbnormalJoyPoint.isPresent() && newAbnormalJoyPoint.isPresent()) {
                newAbnormalJoyPoint.ifPresent((newCap) -> oldAbnormalJoyPoint.ifPresent((oldCap) -> newCap.set(oldCap.get())));
            }
            LazyOptional<CoolDown> oldCoolDown = event.getOriginal().getCapability(CapabilityManager.COOL_DOWN_CAPABILITY);
            LazyOptional<CoolDown> newCoolDown = event.getPlayer().getCapability(CapabilityManager.COOL_DOWN_CAPABILITY);
            if (oldCoolDown.isPresent() && newCoolDown.isPresent()) {
                newCoolDown.ifPresent((newCap) -> oldCoolDown.ifPresent((oldCap) -> newCap.setMap(oldCap.getMap())));
            }
            LazyOptional<LastSweetDreamCapability> oldLastSweetDreamItem = event.getOriginal().getCapability(CapabilityManager.LAST_SWEET_DREAM_CAPABILITY);
            LazyOptional<LastSweetDreamCapability> newLastSweetDreamItem = event.getPlayer().getCapability(CapabilityManager.LAST_SWEET_DREAM_CAPABILITY);
            if (oldLastSweetDreamItem.isPresent() && newLastSweetDreamItem.isPresent()) {
                newLastSweetDreamItem.ifPresent((newCap) -> oldLastSweetDreamItem.ifPresent((oldCap) -> newCap.saveItemStack(oldCap.getItemStack())));
            }
        }
    }
}
