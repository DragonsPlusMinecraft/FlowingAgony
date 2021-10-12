package love.marblegate.flowingagony.util;

import love.marblegate.flowingagony.effect.implicit.ImplicitBaseEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectUtil {
    public static MobEffectInstance genImplicitEffect(MobEffect effect, int duration) {
        return genImplicitEffect(effect, duration, 0);
    }

    public static MobEffectInstance genImplicitEffect(MobEffect effect, int duration, int amplifier) {
        return new MobEffectInstance(effect, duration, amplifier, false, false);
    }

    public static boolean isImplicit(MobEffectInstance mobEffectInstance) {
        return mobEffectInstance.getEffect() instanceof ImplicitBaseEffect || !mobEffectInstance.isVisible() || !mobEffectInstance.showIcon();
    }

    public static boolean isExplicit(MobEffectInstance mobEffectInstance) {
        return !isImplicit(mobEffectInstance);
    }
}
