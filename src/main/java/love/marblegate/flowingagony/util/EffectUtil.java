package love.marblegate.flowingagony.util;

import love.marblegate.flowingagony.effect.special.ImplicitBaseEffect;
import net.minecraft.potion.EffectInstance;

public class EffectUtil {
    public static boolean isEffectHidden(EffectInstance effect){
        return effect.getPotion() instanceof ImplicitBaseEffect || !(effect.shouldRender() || effect.shouldRenderHUD() || effect.shouldRenderInvText());
    }

    public static boolean isEffectShown(EffectInstance effect){
        return !isEffectHidden(effect);
    }
}
