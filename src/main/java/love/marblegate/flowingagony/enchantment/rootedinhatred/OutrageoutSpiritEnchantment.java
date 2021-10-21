package love.marblegate.flowingagony.enchantment.rootedinhatred;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentCategory;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class OutrageoutSpiritEnchantment extends Enchantment {
    public OutrageoutSpiritEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, CustomEnchantmentCategory.AXE_AND_SWORD, EquipmentSlotTypeSet.MAIN_HAND);
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
        return Configuration.AcquirableSetting.OUTRAGEOUS_SPIRIT.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.OUTRAGEOUS_SPIRIT.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.OUTRAGEOUS_SPIRIT.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.OUTRAGEOUS_SPIRIT.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
