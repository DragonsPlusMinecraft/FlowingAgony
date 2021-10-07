package love.marblegate.flowingagony.util;

import love.marblegate.flowingagony.effect.implicit.ImplicitBaseEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectUtil {
    public static boolean isEffectHidden(MobEffectInstance effect) {
        return effect.getEffect() instanceof ImplicitBaseEffect || !effect.isVisible() || !effect.showIcon();
    }

    public static boolean isEffectShown(MobEffectInstance effect) {
        return !isEffectHidden(effect);
    }
}
