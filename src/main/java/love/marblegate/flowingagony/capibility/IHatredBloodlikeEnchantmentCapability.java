package love.marblegate.flowingagony.capibility;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHatredBloodlikeEnchantmentCapability extends INBTSerializable<CompoundNBT> {
    int getActiveLevel();

    void setActiveLevel(int level);
}
