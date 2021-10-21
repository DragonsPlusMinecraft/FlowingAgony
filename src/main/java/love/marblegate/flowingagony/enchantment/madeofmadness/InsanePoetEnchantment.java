package love.marblegate.flowingagony.enchantment.madeofmadness;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class InsanePoetEnchantment extends Enchantment {
    public InsanePoetEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BOW, EquipmentSlotTypeSet.MAIN_HAND);
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
    public boolean checkCompatibility(Enchantment p_77326_1_) {
        return super.checkCompatibility(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.AGONY_SCREAMER.get() && p_77326_1_ != Enchantments.POWER_ARROWS && p_77326_1_ != Enchantments.PUNCH_ARROWS;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.INSANE_POET.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.INSANE_POET.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.INSANE_POET.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.INSANE_POET.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
