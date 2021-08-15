package love.marblegate.flowingagony.capibility.cooldown;

import java.util.HashMap;
import java.util.Map;

public class CoolDownStandardImpl implements ICoolDown {
    private Map<CoolDownType, Integer> coolDownMap;

    public CoolDownStandardImpl() {
        coolDownMap = new HashMap<>();
        for (CoolDownType coolDownType : CoolDownType.values()) {
            coolDownMap.put(coolDownType, 0);
        }
    }

    @Override
    public int get(CoolDownType coolDownType) {
        return coolDownMap.get(coolDownType);
    }

    @Override
    public boolean isReady(CoolDownType coolDownType) {
        return coolDownMap.get(coolDownType) < 1;
    }

    @Override
    public void set(CoolDownType coolDownType, int coolDownTick) {
        coolDownMap.put(coolDownType, Math.max(0, coolDownTick));
    }

    @Override
    public void decrease(CoolDownType coolDownType) {
        if (!isReady(coolDownType))
            coolDownMap.put(coolDownType, coolDownMap.get(coolDownType) - 1);
    }

    @Override
    public Map<CoolDownType, Integer> getMap() {
        return coolDownMap;
    }

    @Override
    public void setMap(Map<CoolDownType, Integer> coolDownMap) {
        this.coolDownMap = coolDownMap;
    }
}
