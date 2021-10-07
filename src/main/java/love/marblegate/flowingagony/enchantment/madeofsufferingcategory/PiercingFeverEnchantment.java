package love.marblegate.flowingagony.enchantment.madeofsufferingcategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

public class PiercingFeverEnchantment extends Enchantment {
    public PiercingFeverEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
    public boolean checkCompatibility(Enchantment p_77326_1_) {
        return super.checkCompatibility(p_77326_1_)
                && p_77326_1_ != EnchantmentRegistry.CONSTRAINED_HEART.get()
                && p_77326_1_ != EnchantmentRegistry.DESTRUCTION_WORSHIP.get();
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.PIERCING_FEVER.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.PIERCING_FEVER.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.PIERCING_FEVER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.PIERCING_FEVER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
