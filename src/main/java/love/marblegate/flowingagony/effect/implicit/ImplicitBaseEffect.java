package love.marblegate.flowingagony.effect.implicit;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ImplicitBaseEffect extends MobEffect {
    public ImplicitBaseEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    public ImplicitBaseEffect(MobEffectCategory typeIn) {
        super(typeIn, 0);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
