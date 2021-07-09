package love.marblegate.flowingagony.capibility.lastsweetdream;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LastSweetDreamCapabilityStardardImpl implements ILastSweetDreamCapability{
    private ItemStack itemStack;

    public LastSweetDreamCapabilityStardardImpl() {
        this.itemStack = Items.AIR.getDefaultInstance();
    }

    @Override
    public ItemStack getItemStack() {
        return this.itemStack.copy();
    }

    @Override
    public void saveItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
    }

    @Override
    public void clear() {
        this.itemStack = Items.AIR.getDefaultInstance();
    }

    @Override
    public boolean isEmpty() {
        return this.itemStack.getItem() == Items.AIR;
    }

}
