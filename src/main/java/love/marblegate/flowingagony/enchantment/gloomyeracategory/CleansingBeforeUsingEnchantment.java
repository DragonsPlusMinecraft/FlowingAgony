package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class CleansingBeforeUsingEnchantment extends Enchantment {
    public CleansingBeforeUsingEnchantment(Rarity p_i46731_1_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, CustomEnchantmentType.ALMOST_EVERYTHING , p_i46731_3_);
    }

    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_77321_1_,true);
    }

    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_223551_1_,false);
    }

    public int getMaxLevel() {
        return 1;
    }


    public boolean isTreasureEnchantment() {
        return true;
    }

    public boolean canVillagerTrade() { return Config.CLEANSING_BEFORE_USING.get(); }

    public boolean canGenerateInLoot() { return Config.CLEANSING_BEFORE_USING.get(); }

    public boolean isAllowedOnBooks() {
        return Config.CLEANSING_BEFORE_USING.get();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.CLEANSING_BEFORE_USING.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
