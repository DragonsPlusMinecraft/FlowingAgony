package love.marblegate.flowingagony.capibility.cooldown;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoolDownProvider implements ICapabilitySerializable<CompoundNBT> {
    private final ICoolDown imp = new CoolDownStandardImpl();
    private final LazyOptional<ICoolDown> impOptional = LazyOptional.of(() -> imp);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CoolDown.COOL_DOWN_CAPABILITY) {
            return impOptional.cast();
        } else return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CoolDown.COOL_DOWN_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CoolDown.COOL_DOWN_CAPABILITY.writeNBT(imp, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CoolDown.COOL_DOWN_CAPABILITY != null) {
            CoolDown.COOL_DOWN_CAPABILITY.readNBT(imp, null, nbt);
        }
    }
}
