package love.marblegate.flowingagony.capibility;

import net.minecraft.nbt.CompoundNBT;

public class HatredBloodlineEnchantmentCapability implements IHatredBloodlikeEnchantmentCapability {
    private int hatredBloodlineLevel;

    public HatredBloodlineEnchantmentCapability(){
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
