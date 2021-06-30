package love.marblegate.flowingagony.capibility;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlinestatus.HatredBloodlineStatusCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlinestatus.IHatredBloodlineStatusCapability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CapabilityTransfer {

    @SubscribeEvent
    public static void migrateCapDataWhenPlayerRespawn(PlayerEvent.Clone event) {
        if(!event.getPlayer().world.isRemote()){
            LazyOptional<IHatredBloodlineStatusCapability> oldHatredBloodStatus = event.getOriginal().getCapability(HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY);
            LazyOptional<IHatredBloodlineStatusCapability> newHatredBloodStatus = event.getPlayer().getCapability(HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY);
            if (oldHatredBloodStatus.isPresent() && newHatredBloodStatus.isPresent()) {
                newHatredBloodStatus.ifPresent((newCap) -> oldHatredBloodStatus.ifPresent((oldCap) -> newCap.setActiveLevel(oldCap.getActiveLevel())));
            }
            LazyOptional<IAbnormalJoyCapability> oldAbnormalJoyPoint = event.getOriginal().getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
            LazyOptional<IAbnormalJoyCapability> newAbnormalJoyPoint = event.getPlayer().getCapability(AbnormalJoyCapability.ABNORMALJOY_CAPABILITY);
            if (oldAbnormalJoyPoint.isPresent() && newAbnormalJoyPoint.isPresent()) {
                newAbnormalJoyPoint.ifPresent((newCap) -> oldAbnormalJoyPoint.ifPresent((oldCap) -> newCap.set(oldCap.get())));
            }
        }
    }
}
