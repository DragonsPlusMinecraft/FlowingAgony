package love.marblegate.flowingagony.enchantment.innerpotential;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class MiraculousEscapeEnchantment extends Enchantment {
    public MiraculousEscapeEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_FEET, EquipmentSlotTypeSet.FEET);
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
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.MIRACULOUS_ESCAPE.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.MIRACULOUS_ESCAPE.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.MIRACULOUS_ESCAPE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.MIRACULOUS_ESCAPE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
