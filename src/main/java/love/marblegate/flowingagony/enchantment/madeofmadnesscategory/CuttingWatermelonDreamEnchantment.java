package love.marblegate.flowingagony.enchantment.madeofmadnesscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class CuttingWatermelonDreamEnchantment extends Enchantment {
    public CuttingWatermelonDreamEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.DIGGER, EquipmentSlotTypeSet.MAIN_HAND);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() { return Configuration.CUTTING_WATERMELON_DREAM.get(); }

    @Override
    public boolean canGenerateInLoot() { return Configuration.CUTTING_WATERMELON_DREAM.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.CUTTING_WATERMELON_DREAM.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Configuration.CUTTING_WATERMELON_DREAM.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
