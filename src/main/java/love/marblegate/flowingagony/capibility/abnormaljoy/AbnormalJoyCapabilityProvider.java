package love.marblegate.flowingagony.capibility.abnormaljoy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbnormalJoyCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    private final IAbnormalJoyCapability imp = new AbnormalJoyCapabilityStandardImpl();
    private final LazyOptional<IAbnormalJoyCapability> impOptional = LazyOptional.of(() -> imp);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == AbnormalJoyCapability.ABNORMALJOY_CAPABILITY) {
            return impOptional.cast();
        } else return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (AbnormalJoyCapability.ABNORMALJOY_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) AbnormalJoyCapability.ABNORMALJOY_CAPABILITY.writeNBT(imp, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (AbnormalJoyCapability.ABNORMALJOY_CAPABILITY != null) {
            AbnormalJoyCapability.ABNORMALJOY_CAPABILITY.readNBT(imp, null, nbt);
        }
    }
}
