package love.marblegate.flowingagony.capibility;

import love.marblegate.flowingagony.registry.ModCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbnormalJoyCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private IAbnormalJoyCapability abnormalJoyCapability;
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapability.ABNORMALJOY_CAPABILITY ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    @Nonnull
    IAbnormalJoyCapability getOrCreateCapability() {
        if (abnormalJoyCapability == null) {
            this.abnormalJoyCapability = new AbnormalJoyCapability();
        }
        return this.abnormalJoyCapability;
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
