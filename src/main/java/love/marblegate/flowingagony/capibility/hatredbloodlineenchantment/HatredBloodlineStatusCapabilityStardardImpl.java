package love.marblegate.flowingagony.capibility.hatredbloodlineenchantment;

import net.minecraft.nbt.CompoundNBT;

public class HatredBloodlineStatusCapabilityStardardImpl implements IHatredBloodlineStatusCapability {
    private int hatredBloodlineLevel;

    public HatredBloodlineStatusCapabilityStardardImpl(){
        this.hatredBloodlineLevel = 0;
    }

    @Override
    public int getActiveLevel() {
        return hatredBloodlineLevel;
    }

    @Override
    public void setActiveLevel(int level) {
        this.hatredBloodlineLevel = level;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("hatred_bloodline_level", this.hatredBloodlineLevel);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.hatredBloodlineLevel = nbt.getInt("hatred_bloodline_level");
    }


}
