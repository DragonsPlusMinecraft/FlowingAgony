package love.marblegate.flowingagony.effect.special;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.ArrayList;
import java.util.List;

public class ImplicitBaseEffect extends Effect {
    public ImplicitBaseEffect() {
        super(EffectType.NEUTRAL, 0);
    }

    public ImplicitBaseEffect(EffectType typeIn) {
        super(typeIn, 0);
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
