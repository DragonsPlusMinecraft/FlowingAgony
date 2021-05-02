package love.marblegate.flowingagony.capibility;

import net.minecraft.nbt.CompoundNBT;

public class AbnormalJoyCapability implements IAbnormalJoyCapability {
    private float abnormalJoyPoint;

    public AbnormalJoyCapability(){
        this.abnormalJoyPoint = 0;
    }
    @Override
    public float get() {
        return abnormalJoyPoint;
    }

    @Override
    public void add(float point) {
        if(abnormalJoyPoint+point>100) abnormalJoyPoint=100;
        else abnormalJoyPoint+=point;
    }

    @Override
    public void decrease(float point) {
        if(abnormalJoyPoint-point<0) abnormalJoyPoint=0;
        else abnormalJoyPoint-=point;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("abnormal_joy",abnormalJoyPoint);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        abnormalJoyPoint = nbt.getFloat("abnormal_joy");
    }
}
