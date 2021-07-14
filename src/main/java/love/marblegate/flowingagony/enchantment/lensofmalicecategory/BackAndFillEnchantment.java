package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class BackAndFillEnchantment extends Enchantment{
    public BackAndFillEnchantment() {
        super(Rarity.VERY_RARE, CustomEnchantmentType.BOW_AND_CROSSBOW, EquipmentSlotTypeSet.MAIN_HAND);
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
        return 3;
    }


    @Override
    public boolean canVillagerTrade() { return Config.BACK_AND_FILL.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.BACK_AND_FILL.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.BACK_AND_FILL.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.I_SEE_YOU_NOW.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
