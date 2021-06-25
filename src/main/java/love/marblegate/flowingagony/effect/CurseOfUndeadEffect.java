package love.marblegate.flowingagony.effect;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.util.ArrayList;
import java.util.List;

public class CurseOfUndeadEffect extends Effect {
    public CurseOfUndeadEffect() {
        super(EffectType.HARMFUL, 0);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
        return ret;
    }
}
