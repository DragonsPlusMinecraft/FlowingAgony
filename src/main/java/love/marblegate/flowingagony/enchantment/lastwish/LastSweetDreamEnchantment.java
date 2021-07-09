package love.marblegate.flowingagony.enchantment.lastwish;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class LastSweetDreamEnchantment extends Enchantment {
    public LastSweetDreamEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, EquipmentSlotTypeSet.ALL);
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
    public boolean canVillagerTrade() { return Config.LAST_SWEET_DREAM.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.LAST_SWEET_DREAM.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.LAST_SWEET_DREAM.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.LAST_SWEET_DREAM.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
