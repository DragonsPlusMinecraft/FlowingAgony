package love.marblegate.flowingagony.enchantment.rootedinhatredcategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class OutrageoutSpiritEnchantment extends Enchantment{
    public OutrageoutSpiritEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, CustomEnchantmentType.AXE_AND_SWORD, EquipmentSlotTypeSet.MAINHAND);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canVillagerTrade() { return Config.OUTRAGEOUS_SPIRIT.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.OUTRAGEOUS_SPIRIT.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.OUTRAGEOUS_SPIRIT.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.OUTRAGEOUS_SPIRIT.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
