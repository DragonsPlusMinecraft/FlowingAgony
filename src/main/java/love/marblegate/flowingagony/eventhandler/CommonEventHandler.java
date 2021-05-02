package love.marblegate.flowingagony.eventhandler;

import love.marblegate.flowingagony.capibility.AbnormalJoyCapabilityProvider;
import love.marblegate.flowingagony.capibility.HatredBloodloneEnchantmentCapabilityProvider;
import love.marblegate.flowingagony.capibility.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.IHatredBloodlikeEnchantmentCapability;
import love.marblegate.flowingagony.registry.ModCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CommonEventHandler {
    //I really want to move cap attach to relative EnchantmentEventHandler!!!!!
    //This categorizing method makes me uncomfortable.

    @SubscribeEvent
    public static void attachCap(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation("flowingagony", "hatred_bloodline_level"), new HatredBloodloneEnchantmentCapabilityProvider());
            event.addCapability(new ResourceLocation("flowingagony","abnormal_joy_point"),new AbnormalJoyCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void migrateCapDataWhenPlayerRespawn(PlayerEvent.Clone event) {
        LazyOptional<IHatredBloodlikeEnchantmentCapability> oldHatredBloodStatus = event.getOriginal().getCapability(ModCapability.HATRED_BLOODLINE_CAPABILITY);
        LazyOptional<IHatredBloodlikeEnchantmentCapability> newHatredBloodStatus = event.getPlayer().getCapability(ModCapability.HATRED_BLOODLINE_CAPABILITY);
        if (oldHatredBloodStatus.isPresent() && newHatredBloodStatus.isPresent()) {
            newHatredBloodStatus.ifPresent((newCap) -> {
                oldHatredBloodStatus.ifPresent((oldCap) -> {
                    newCap.deserializeNBT(oldCap.serializeNBT());
                });
            });
        }
        LazyOptional<IAbnormalJoyCapability> oldAbnormalJoyPoint = event.getOriginal().getCapability(ModCapability.ABNORMALJOY_CAPABILITY);
        LazyOptional<IAbnormalJoyCapability> newAbnormalJoyPoint = event.getPlayer().getCapability(ModCapability.ABNORMALJOY_CAPABILITY);
        if (oldAbnormalJoyPoint.isPresent() && newAbnormalJoyPoint.isPresent()) {
            newAbnormalJoyPoint.ifPresent((newCap) -> {
                oldAbnormalJoyPoint.ifPresent((oldCap) -> {
                    newCap.deserializeNBT(oldCap.serializeNBT());
                });
            });
        }
    }
}
