package love.marblegate.flowingagony.capibility;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LastSweetDreamCapability {
    private ItemStack itemStack;

    public LastSweetDreamCapability() {
        itemStack = Items.AIR.getDefaultInstance();
    }

    public ItemStack getItemStack() {
        return itemStack.copy();
    }

    public void saveItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
    }

    public void clear() {
        itemStack = Items.AIR.getDefaultInstance();
    }

    public boolean isEmpty() {
        return itemStack.getItem() == Items.AIR;
    }

}
