package love.marblegate.flowingagony.eventhandler;

import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
import love.marblegate.flowingagony.capibility.hatredbloodlineenchantment.IHatredBloodlikeEnchantmentCapability;
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
                    IHatredBloodlikeEnchantmentCapability.class,
                    new Capability.IStorage<IHatredBloodlikeEnchantmentCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<IHatredBloodlikeEnchantmentCapability> capability, IHatredBloodlikeEnchantmentCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<IHatredBloodlikeEnchantmentCapability> capability, IHatredBloodlikeEnchantmentCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );
        });
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(
                    IAbnormalJoyCapability.class,
                    new Capability.IStorage<IAbnormalJoyCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<IAbnormalJoyCapability> capability, IAbnormalJoyCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<IAbnormalJoyCapability> capability, IAbnormalJoyCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );
        });
    }
}
