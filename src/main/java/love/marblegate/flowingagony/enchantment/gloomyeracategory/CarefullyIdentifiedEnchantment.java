package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class CarefullyIdentifiedEnchantment extends Enchantment {
    public CarefullyIdentifiedEnchantment() {
        super(Rarity.VERY_RARE, CustomEnchantmentType.PICKAXE , EquipmentSlotTypeSet.MAIN_HAND);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(this.getRarity(),this.getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(this.getRarity(),this.getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


    @Override
    public boolean canVillagerTrade() {
        return Config.CAREFULLY_IDENTIFIED.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Config.CAREFULLY_IDENTIFIED.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.CAREFULLY_IDENTIFIED.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.CAREFULLY_IDENTIFIED.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
