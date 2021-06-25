package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class DirtyMoneyEnchantment extends Enchantment {
    public DirtyMoneyEnchantment(Rarity p_i46731_1_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, EnchantmentType.ARMOR, p_i46731_3_);
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
        return 2;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.come_back_at_dusk_enchantment.get() && p_77326_1_ != Enchantments.MENDING;
    }

    @Override
    public boolean canVillagerTrade() { return Config.DIRTY_MONEY.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.DIRTY_MONEY.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.DIRTY_MONEY.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.DIRTY_MONEY.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
