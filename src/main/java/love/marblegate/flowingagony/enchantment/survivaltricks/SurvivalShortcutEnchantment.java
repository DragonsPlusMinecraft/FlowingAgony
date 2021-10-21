package love.marblegate.flowingagony.enchantment.survivaltricks;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SurvivalShortcutEnchantment extends Enchantment {
    public SurvivalShortcutEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
        return super.checkCompatibility(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.SURVIVAL_RUSE.get() && p_77326_1_ != EnchantmentRegistry.NECESSARY_EVIL.get();
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.SURVIVAL_SHORTCUT.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.SURVIVAL_SHORTCUT.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.SURVIVAL_SHORTCUT.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.SURVIVAL_SHORTCUT.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
