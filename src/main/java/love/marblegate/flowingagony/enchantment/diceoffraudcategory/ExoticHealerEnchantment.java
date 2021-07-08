package love.marblegate.flowingagony.enchantment.diceoffraudcategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ExoticHealerEnchantment extends Enchantment{
    public ExoticHealerEnchantment() {
        super(Enchantment.Rarity.VERY_RARE,EnchantmentType.ARMOR, EquipmentSlotTypeSet.ARMORS);
}

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canVillagerTrade() { return Config.EXOTIC_HEALER.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.EXOTIC_HEALER.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.EXOTIC_HEALER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.EXOTIC_HEALER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
