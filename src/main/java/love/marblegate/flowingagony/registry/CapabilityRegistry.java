package love.marblegate.flowingagony.registry;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapabilityStandardImpl;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.HatredBloodlineStatusCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.HatredBloodlineStatusCapabilityStardardImpl;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.IHatredBloodlineStatusCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {
    @SubscribeEvent
    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        HatredBloodlineStatusCapability.register();
        AbnormalJoyCapability.register();
    }
}
