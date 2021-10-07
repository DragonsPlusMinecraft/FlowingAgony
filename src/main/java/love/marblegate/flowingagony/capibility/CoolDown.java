package love.marblegate.flowingagony.capibility;

import java.util.HashMap;
import java.util.Map;

public class CoolDown {
    private Map<CoolDownType, Integer> coolDownMap;

    public CoolDown() {
        coolDownMap = new HashMap<>();
        for (CoolDownType coolDownType : CoolDownType.values()) {
            coolDownMap.put(coolDownType, 0);
        }
    }

    public int get(CoolDownType coolDownType) {
        return coolDownMap.get(coolDownType);
    }

    public boolean isReady(CoolDownType coolDownType) {
        return coolDownMap.get(coolDownType) < 1;
    }

    public void set(CoolDownType coolDownType, int coolDownTick) {
        coolDownMap.put(coolDownType, Math.max(0, coolDownTick));
    }

    public void decrease(CoolDownType coolDownType) {
        if (!isReady(coolDownType))
            coolDownMap.put(coolDownType, coolDownMap.get(coolDownType) - 1);
    }

    public Map<CoolDownType, Integer> getMap() {
        return coolDownMap;
    }

    public void setMap(Map<CoolDownType, Integer> coolDownMap) {
        this.coolDownMap = coolDownMap;
    }

    public enum CoolDownType {
        AN_ENCHANTED_GOLDEN_APPLE_A_DAY,
        MORIRS_DEATHWISH_DEATHMENDING
    }
}
