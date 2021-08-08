package love.marblegate.flowingagony.enchantment.flameofenvycategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class CovertKnifeEnchantment extends Enchantment {
    public CovertKnifeEnchantment() {
        super(Rarity.RARE, EnchantmentType.BOW, EquipmentSlotTypeSet.MAIN_HAND);
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
    public boolean canVillagerTrade() { return Configuration.COVERT_KNIFE.get(); }

    @Override
    public boolean canGenerateInLoot() { return Configuration.COVERT_KNIFE.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.COVERT_KNIFE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Configuration.COVERT_KNIFE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
