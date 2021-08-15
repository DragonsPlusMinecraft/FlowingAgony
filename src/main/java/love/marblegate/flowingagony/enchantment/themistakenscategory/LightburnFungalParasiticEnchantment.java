package love.marblegate.flowingagony.enchantment.themistakenscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class LightburnFungalParasiticEnchantment extends Enchantment {
    public LightburnFungalParasiticEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.LIGHTBURN_FUNGAL_PARASITIC.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.LIGHTBURN_FUNGAL_PARASITIC.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.LIGHTBURN_FUNGAL_PARASITIC.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.LIGHTBURN_FUNGAL_PARASITIC.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
