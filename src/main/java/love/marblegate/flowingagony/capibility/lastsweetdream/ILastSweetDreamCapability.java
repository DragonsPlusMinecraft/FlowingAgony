package love.marblegate.flowingagony.capibility.lastsweetdream;

import net.minecraft.item.ItemStack;

public interface ILastSweetDreamCapability {
    ItemStack getItemStack();

    void saveItemStack(ItemStack itemStack);

    void clear();

    boolean isEmpty();
}
