package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MaliceOutbreakEnchantment extends Enchantment {
    public MaliceOutbreakEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlotTypeSet.HEAD);
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
        return Configuration.AcquirableSetting.MALICE_OUTBREAK.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.MALICE_OUTBREAK.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.MALICE_OUTBREAK.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.MALICE_OUTBREAK.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
