package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class InfectiousMaliceEnchantment extends Enchantment{
    public InfectiousMaliceEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR, EquipmentSlotTypeSet.ARMORS);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_);
    }

    @Override
    public boolean canVillagerTrade() { return Configuration.INFECTIOUS_MALICE.get(); }

    @Override
    public boolean canGenerateInLoot() { return Configuration.INFECTIOUS_MALICE.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.INFECTIOUS_MALICE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Configuration.INFECTIOUS_MALICE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
