package love.marblegate.flowingagony.enchantment.innerpotentialcategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

public class FrivolousStepEnchantment extends Enchantment {
    public FrivolousStepEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlotTypeSet.FEET);
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
        return 2;
    }

    @Override
    public boolean checkCompatibility(Enchantment p_77326_1_) {
        return super.checkCompatibility(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.STUBBORN_STEP.get();
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.FRIVOLOUS_STEP.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.FRIVOLOUS_STEP.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.FRIVOLOUS_STEP.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.FRIVOLOUS_STEP.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
