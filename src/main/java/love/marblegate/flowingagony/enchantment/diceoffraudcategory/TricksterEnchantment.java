package love.marblegate.flowingagony.enchantment.diceoffraudcategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class TricksterEnchantment extends Enchantment {
    public TricksterEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, CustomEnchantmentType.AXE_AND_SWORD, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
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
        return 2;
    }

    @Override
    public boolean canVillagerTrade() { return Config.TRICKSTER.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.TRICKSTER.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.TRICKSTER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.TRICKSTER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
