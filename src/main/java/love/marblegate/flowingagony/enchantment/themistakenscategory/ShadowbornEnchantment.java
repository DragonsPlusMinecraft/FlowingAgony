package love.marblegate.flowingagony.enchantment.themistakenscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class ShadowbornEnchantment extends Enchantment {
    public ShadowbornEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotTypeSet.HEAD);
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
        return Configuration.AcquirableSetting.SHADOWBORN.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.SHADOWBORN.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.SHADOWBORN.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.SHADOWBORN.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
