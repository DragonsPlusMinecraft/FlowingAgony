package love.marblegate.flowingagony.enchantment.madeofmadnesscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

public class CuttingWatermelonDreamEnchantment extends Enchantment {
    public CuttingWatermelonDreamEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlotTypeSet.MAIN_HAND);
    }

    @Override
    public int getMinCost(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_77321_1_, true);
    }

    @Override
    public int getMaxCost(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_223551_1_, false);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.CUTTING_WATERMELON_DREAM.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.CUTTING_WATERMELON_DREAM.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.CUTTING_WATERMELON_DREAM.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.CUTTING_WATERMELON_DREAM.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
