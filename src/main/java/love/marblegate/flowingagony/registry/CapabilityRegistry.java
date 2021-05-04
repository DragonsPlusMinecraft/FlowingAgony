package love.marblegate.flowingagony.registry;

import love.marblegate.flowingagony.capibility.abnormaljoy.AbnormalJoyCapabilityStandardImpl;
import love.marblegate.flowingagony.capibility.abnormaljoy.IAbnormalJoyCapability;
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
        event.enqueueWork(() -> {
                CapabilityManager.INSTANCE.register(
                    IHatredBloodlineStatusCapability.class,
                    new Capability.IStorage<IHatredBloodlineStatusCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<IHatredBloodlineStatusCapability> capability, IHatredBloodlineStatusCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<IHatredBloodlineStatusCapability> capability, IHatredBloodlineStatusCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    HatredBloodlineStatusCapabilityStardardImpl::new
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
                    AbnormalJoyCapabilityStandardImpl::new
            );
        });
    }
}
