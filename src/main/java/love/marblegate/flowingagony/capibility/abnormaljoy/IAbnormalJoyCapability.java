package love.marblegate.flowingagony.capibility.abnormaljoy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAbnormalJoyCapability {

    float get();

    void set(float level);

    void add(float level);

    void decrease(float level);
}
