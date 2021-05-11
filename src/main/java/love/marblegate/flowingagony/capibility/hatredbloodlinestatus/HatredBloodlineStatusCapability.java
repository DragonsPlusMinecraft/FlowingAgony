package love.marblegate.flowingagony.capibility.hatredbloodlinestatus;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class HatredBloodlineStatusCapability {
    @CapabilityInject(IHatredBloodlineStatusCapability.class)
    public static Capability<IHatredBloodlineStatusCapability> HATRED_BLOODLINE_STATUS_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IHatredBloodlineStatusCapability.class, new Storage(), HatredBloodlineStatusCapabilityStardardImpl::new);
    }

    public static class Storage implements Capability.IStorage<IHatredBloodlineStatusCapability> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IHatredBloodlineStatusCapability> capability, IHatredBloodlineStatusCapability instance, Direction side) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putInt("hatred_bloodline_level", instance.getActiveLevel());
            return compoundNBT;
        }

        @Override
        public void readNBT(Capability<IHatredBloodlineStatusCapability> capability, IHatredBloodlineStatusCapability instance, Direction side, INBT nbt) {
            int hatredBloodlineLevel = ((CompoundNBT)nbt).getInt("hatred_bloodline_level");
            instance.setActiveLevel(hatredBloodlineLevel);
        }
    }
}
