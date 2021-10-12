package love.marblegate.flowingagony.capibility;

public class AbnormalJoyCapability {
    private float abnormalJoyPoint;

    public AbnormalJoyCapability() {
        abnormalJoyPoint = 0;
    }

    public float get() {
        return abnormalJoyPoint;
    }

    public void set(float level) {
        abnormalJoyPoint = level;
    }

    public void add(float point) {
        if (abnormalJoyPoint + point > 100) abnormalJoyPoint = 100;
        else abnormalJoyPoint += point;
    }

    public void decrease(float point) {
        if (abnormalJoyPoint - point < 0) abnormalJoyPoint = 0;
        else abnormalJoyPoint -= point;
    }
}
