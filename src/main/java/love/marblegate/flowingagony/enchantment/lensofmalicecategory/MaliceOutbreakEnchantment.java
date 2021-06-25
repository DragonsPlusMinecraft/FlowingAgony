package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class MaliceOutbreakEnchantment extends Enchantment{
    public MaliceOutbreakEnchantment(Rarity p_i46731_1_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, EnchantmentType.ARMOR_HEAD, p_i46731_3_);
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
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.vengeance_enchantment.get();
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
