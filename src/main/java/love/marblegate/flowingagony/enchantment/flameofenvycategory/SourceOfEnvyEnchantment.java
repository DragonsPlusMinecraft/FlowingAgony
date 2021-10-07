package love.marblegate.flowingagony.enchantment.flameofenvycategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class SourceOfEnvyEnchantment extends Enchantment {
    public SourceOfEnvyEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
        return 5;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.SOURCE_OF_ENVY.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.SOURCE_OF_ENVY.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.SOURCE_OF_ENVY.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.SOURCE_OF_ENVY.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
