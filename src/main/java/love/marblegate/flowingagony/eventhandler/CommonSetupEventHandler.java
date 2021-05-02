package love.marblegate.flowingagony.eventhandler;

import love.marblegate.flowingagony.capibility.AbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.HatredBloodlineEnchantmentCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEventHandler {
    @SubscribeEvent
    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(
                    HatredBloodlineEnchantmentCapability.class,
                    new Capability.IStorage<HatredBloodlineEnchantmentCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<HatredBloodlineEnchantmentCapability> capability, HatredBloodlineEnchantmentCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<HatredBloodlineEnchantmentCapability> capability, HatredBloodlineEnchantmentCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );
        });
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(
                    AbnormalJoyCapability.class,
                    new Capability.IStorage<AbnormalJoyCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<AbnormalJoyCapability> capability, AbnormalJoyCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<AbnormalJoyCapability> capability, AbnormalJoyCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );
        });
    }
}
