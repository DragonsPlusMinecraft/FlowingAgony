package love.marblegate.flowingagony.capibility;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAbnormalJoyCapability extends INBTSerializable<CompoundNBT> {

    float get();

    void add(float level);

    void decrease(float level);
}
