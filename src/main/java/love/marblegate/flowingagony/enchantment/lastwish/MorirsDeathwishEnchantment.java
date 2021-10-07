package love.marblegate.flowingagony.enchantment.lastwish;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;

public class MorirsDeathwishEnchantment extends Enchantment {
    public MorirsDeathwishEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlotTypeSet.ALL);
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
                && p_77326_1_ != EnchantmentRegistry.MORIRS_LIFEBOUND.get()
                && p_77326_1_ != EnchantmentRegistry.GUIDENS_REGRET.get()
                && p_77326_1_ != Enchantments.MENDING;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.MORIRS_DEATHWISH.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.MORIRS_DEATHWISH.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.MORIRS_DEATHWISH.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.MORIRS_DEATHWISH.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
