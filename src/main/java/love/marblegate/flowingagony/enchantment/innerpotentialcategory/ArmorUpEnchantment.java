package love.marblegate.flowingagony.enchantment.innerpotentialcategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class ArmorUpEnchantment extends Enchantment {
    public ArmorUpEnchantment() {
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
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.ARMOR_UP.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.ARMOR_UP.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.ARMOR_UP.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.ARMOR_UP.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
