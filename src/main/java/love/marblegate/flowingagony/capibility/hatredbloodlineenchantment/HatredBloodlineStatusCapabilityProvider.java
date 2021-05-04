package love.marblegate.flowingagony.capibility.hatredbloodlineenchantment;

import love.marblegate.flowingagony.capibility.ModCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HatredBloodlineStatusCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    private IHatredBloodlineStatusCapability hatredBloodlineStatusCapability;
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapability.HATRED_BLOODLINE_CAPABILITY ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    @Nonnull
    IHatredBloodlineStatusCapability getOrCreateCapability() {
        if (hatredBloodlineStatusCapability == null) {
            this.hatredBloodlineStatusCapability = new HatredBloodlineStatusCapabilityStardardImpl();
        }
        return this.hatredBloodlineStatusCapability;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }
}
