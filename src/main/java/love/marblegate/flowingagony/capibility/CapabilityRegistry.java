package love.marblegate.flowingagony.capibility;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(AbnormalJoyCapability.class);
        event.register(CoolDown.class);
        event.register(HatredBloodlineStatusCapability.class);
        event.register(LastSweetDreamCapability.class);
    }
}
