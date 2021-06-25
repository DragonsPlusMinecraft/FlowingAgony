package love.marblegate.flowingagony.capibility.abnormaljoy;

public class AbnormalJoyCapabilityStandardImpl implements IAbnormalJoyCapability {
    private float abnormalJoyPoint;

    public AbnormalJoyCapabilityStandardImpl(){
        this.abnormalJoyPoint = 0;
    }
    @Override
    public float get() {
        return abnormalJoyPoint;
    }

    @Override
    public void set(float level) {
        this.abnormalJoyPoint = level;
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
}
