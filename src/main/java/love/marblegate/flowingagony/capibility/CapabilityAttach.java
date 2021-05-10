package love.marblegate.flowingagony.capibility;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapabilityProvider;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.HatredBloodlineStatusCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CapabilityAttach {
    @SubscribeEvent
    public static void attachCap(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation("flowingagony", "hatred_bloodline_level"), new HatredBloodlineStatusCapabilityProvider());
            event.addCapability(new ResourceLocation("flowingagony","abnormal_joy_point"),new AbnormalJoyCapabilityProvider());
        }
    }
}
