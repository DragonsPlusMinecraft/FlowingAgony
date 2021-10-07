package love.marblegate.flowingagony.enchantment.themistakenscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

public class OriginalSinErosionEnchantment extends Enchantment {
    public OriginalSinErosionEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment p_77326_1_) {
        return super.checkCompatibility(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.SCHOLAR_OF_ORIGINAL_SIN.get();
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.ORIGINAL_SIN_EROSION.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.ORIGINAL_SIN_EROSION.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.ORIGINAL_SIN_EROSION.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.ORIGINAL_SIN_EROSION.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
