package love.marblegate.flowingagony.capibility.cooldown;

import java.util.Map;

public interface ICoolDown {

    int get(CoolDownType coolDownType);

    boolean isReady(CoolDownType coolDownType);

    void set(CoolDownType coolDownType, int coolDownTick);

    void decrease(CoolDownType coolDownType);

    Map<CoolDownType, Integer> getMap();

    void setMap(Map<CoolDownType, Integer> coolDownMap);
}
