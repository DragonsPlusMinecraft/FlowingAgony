package love.marblegate.flowingagony.enchantment.themistakenscategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class CorrruptedKindredEnchantment extends Enchantment {
    public CorrruptedKindredEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
    public boolean canVillagerTrade() { return Config.CORRUPTED_KINDRED.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.CORRUPTED_KINDRED.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.CORRUPTED_KINDRED.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.CORRUPTED_KINDRED.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
