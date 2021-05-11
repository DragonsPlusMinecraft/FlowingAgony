package love.marblegate.flowingagony.capibility.hatredbloodlinestatus;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HatredBloodlineStatusCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    private final IHatredBloodlineStatusCapability imp = new HatredBloodlineStatusCapabilityStardardImpl();
    private final LazyOptional<IHatredBloodlineStatusCapability> impOptional = LazyOptional.of(() -> imp);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY){
            return impOptional.cast();
        }
        else return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY.writeNBT(imp, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY != null) {
            HatredBloodlineStatusCapability.HATRED_BLOODLINE_STATUS_CAPABILITY.readNBT(imp, null, nbt);
        }
    }
}
