package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class MaliceOutbreakEnchantment extends Enchantment{
    public MaliceOutbreakEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotTypeSet.HEAD);
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
    public boolean canVillagerTrade() { return Config.MALICE_OUTBREAK.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.MALICE_OUTBREAK.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.MALICE_OUTBREAK.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.MALICE_OUTBREAK.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
