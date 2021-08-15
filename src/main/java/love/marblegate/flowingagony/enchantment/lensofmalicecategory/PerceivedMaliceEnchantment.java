package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class PerceivedMaliceEnchantment extends Enchantment {
    public PerceivedMaliceEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotTypeSet.HEAD);
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
        return 5;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.VENGEANCE.get();
    }

    @Override
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.PERCEIVED_MALICE.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.PERCEIVED_MALICE.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.PERCEIVED_MALICE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.PERCEIVED_MALICE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
