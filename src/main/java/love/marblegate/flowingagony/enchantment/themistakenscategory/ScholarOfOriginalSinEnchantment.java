package love.marblegate.flowingagony.enchantment.themistakenscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class ScholarOfOriginalSinEnchantment extends Enchantment {
    public ScholarOfOriginalSinEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.ORIGINAL_SIN_EROSION.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.SCHOLAR_OF_ORIGINAL_SIN.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.SCHOLAR_OF_ORIGINAL_SIN.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.SCHOLAR_OF_ORIGINAL_SIN.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.SCHOLAR_OF_ORIGINAL_SIN.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
