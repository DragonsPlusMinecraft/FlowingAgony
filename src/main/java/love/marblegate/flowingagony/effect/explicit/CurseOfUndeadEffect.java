package love.marblegate.flowingagony.effect.explicit;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class CurseOfUndeadEffect extends MobEffect {
    public CurseOfUndeadEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
        return ret;
    }
}
