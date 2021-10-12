package love.marblegate.flowingagony.enchantment.madeofmadnesscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class PaperBrainEnchantment extends Enchantment {
    public PaperBrainEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlotTypeSet.MAIN_HAND);
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
        return super.checkCompatibility(p_77326_1_)
                && p_77326_1_ != EnchantmentRegistry.SHOCK_THERAPY.get()
                && p_77326_1_ != Enchantments.SHARPNESS
                && p_77326_1_ != Enchantments.BANE_OF_ARTHROPODS
                && p_77326_1_ != Enchantments.SMITE;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.PAPER_BRAIN.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.PAPER_BRAIN.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.PAPER_BRAIN.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.PAPER_BRAIN.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
