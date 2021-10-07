package love.marblegate.flowingagony.enchantment.flameofenvycategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

public class ThornInFleshEnchantment extends Enchantment {
    public ThornInFleshEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.CROSSBOW, EquipmentSlotTypeSet.MAIN_HAND);
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
        return super.checkCompatibility(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.EYESORE.get();
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.THORN_IN_FLESH.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.THORN_IN_FLESH.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.THORN_IN_FLESH.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.THORN_IN_FLESH.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
