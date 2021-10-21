package love.marblegate.flowingagony.enchantment.gloomyera;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class RegularCustomerProgramEnchantment extends Enchantment {
    public RegularCustomerProgramEnchantment() {
        super(Rarity.VERY_RARE, CustomEnchantmentType.AXE_AND_SWORD, EquipmentSlotTypeSet.MAIN_HAND);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_77321_1_, true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_223551_1_, false);
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
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.REGULAR_CUSTOMER_PROGRAM.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.REGULAR_CUSTOMER_PROGRAM.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.REGULAR_CUSTOMER_PROGRAM.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
