package love.marblegate.flowingagony.capibility.cooldown;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CoolDown {
    @CapabilityInject(ICoolDown.class)
    public static final Capability<ICoolDown> COOL_DOWN_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ICoolDown.class, new Storage(), CoolDownStandardImpl::new);
    }

    public static class Storage implements Capability.IStorage<ICoolDown> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<ICoolDown> capability, ICoolDown instance, Direction side) {
            CompoundNBT compoundNBT = new CompoundNBT();
            for(CoolDownType coolDownType:CoolDownType.values()){
                compoundNBT.putInt(coolDownType.name(),instance.get(coolDownType));
            }
            return compoundNBT;
        }

        @Override
        public void readNBT(Capability<ICoolDown> capability, ICoolDown instance, Direction side, INBT nbt) {
            for(CoolDownType coolDownType:CoolDownType.values()){
                int temp = ((CompoundNBT)nbt).getInt(coolDownType.name());
                instance.set(coolDownType,temp);
            }
        }
    }
}
