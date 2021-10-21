package love.marblegate.flowingagony.enchantment.madeofsuffering;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class BurningPhobiaEnchantment extends Enchantment {
    public BurningPhobiaEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotTypeSet.HEAD);
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
        return super.canApplyTogether(p_77326_1_) && p_77326_1_
                != EnchantmentRegistry.DROWNING_PHOBIA.get() && p_77326_1_
                != EnchantmentRegistry.PRAYER_OF_PAIN.get();
    }

    @Override
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.BURNING_PHOBIA.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.BURNING_PHOBIA.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.BURNING_PHOBIA.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.BURNING_PHOBIA.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
