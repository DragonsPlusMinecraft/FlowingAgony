package love.marblegate.flowingagony.enchantment.madeofmadnesscategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class InsanePoetEnchantment extends Enchantment {
    public InsanePoetEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.BOW, EquipmentSlotTypeSet.MAINHAND);
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
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.agony_screamer.get() && p_77326_1_ != Enchantments.POWER && p_77326_1_ != Enchantments.PUNCH;
    }

    @Override
    public boolean canVillagerTrade() { return Config.INSANE_POET.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.INSANE_POET.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.INSANE_POET.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.INSANE_POET.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
