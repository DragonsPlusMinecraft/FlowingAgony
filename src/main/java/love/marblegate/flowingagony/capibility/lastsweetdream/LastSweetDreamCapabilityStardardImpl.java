package love.marblegate.flowingagony.capibility.lastsweetdream;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LastSweetDreamCapabilityStardardImpl implements ILastSweetDreamCapability{
    private ItemStack itemStack;

    public LastSweetDreamCapabilityStardardImpl() {
        itemStack = Items.AIR.getDefaultInstance();
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack.copy();
    }

    @Override
    public void saveItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
    }

    @Override
    public void clear() {
        itemStack = Items.AIR.getDefaultInstance();
    }

    @Override
    public boolean isEmpty() {
        return itemStack.getItem() == Items.AIR;
    }

}
