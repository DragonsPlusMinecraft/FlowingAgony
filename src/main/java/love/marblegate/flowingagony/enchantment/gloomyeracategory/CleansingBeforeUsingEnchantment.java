package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class CleansingBeforeUsingEnchantment extends Enchantment {
    public CleansingBeforeUsingEnchantment() {
        super(Enchantment.Rarity.RARE, CustomEnchantmentType.ALMOST_EVERYTHING , EquipmentSlotType.values());
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
        return 1;
    }


    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return Config.CLEANSING_BEFORE_USING.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Config.CLEANSING_BEFORE_USING.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.CLEANSING_BEFORE_USING.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.CLEANSING_BEFORE_USING.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
