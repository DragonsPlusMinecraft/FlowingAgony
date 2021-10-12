package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentCategory;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class CleansingBeforeUsingEnchantment extends Enchantment {
    public CleansingBeforeUsingEnchantment() {
        super(Enchantment.Rarity.RARE, CustomEnchantmentCategory.ALMOST_EVERYTHING, EquipmentSlotTypeSet.ALL);
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
        return Configuration.AcquirableSetting.CLEANSING_BEFORE_USING.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.CLEANSING_BEFORE_USING.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.CLEANSING_BEFORE_USING.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.CLEANSING_BEFORE_USING.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
