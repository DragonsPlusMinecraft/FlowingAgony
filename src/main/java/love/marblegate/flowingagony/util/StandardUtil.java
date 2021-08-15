package love.marblegate.flowingagony.util;

import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class StandardUtil {
    public static boolean shouldReflectDamage(LivingDamageEvent event) {
        return event.getAmount() > 0.1 && event.getSource().getTrueSource() != event.getEntityLiving();
    }
}
