package love.marblegate.flowingagony.capibility.abnormaljoy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class AbnormalJoyCapability {
    @CapabilityInject(IAbnormalJoyCapability.class)
    public static Capability<IAbnormalJoyCapability> ABNORMALJOY_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IAbnormalJoyCapability.class, new Storage(), AbnormalJoyCapabilityStandardImpl::new);
    }

    public static class Storage implements Capability.IStorage<IAbnormalJoyCapability> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IAbnormalJoyCapability> capability, IAbnormalJoyCapability instance, Direction side) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putFloat("abnormal_joy",instance.get());
            return compoundNBT;
        }

        @Override
        public void readNBT(Capability<IAbnormalJoyCapability> capability, IAbnormalJoyCapability instance, Direction side, INBT nbt) {
            float abnormalJoyPoint = ((CompoundNBT)nbt).getFloat("abnormal_joy");
            instance.set(abnormalJoyPoint);
        }
    }
}
