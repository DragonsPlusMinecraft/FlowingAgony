package love.marblegate.flowingagony.enchantment.flameofenvycategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class EyesoreEnchantment extends Enchantment {
    public EyesoreEnchantment(Rarity p_i46731_1_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, EnchantmentType.CROSSBOW, p_i46731_3_);
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

    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.thorn_in_flesh_enchantment.get();
    }

    public boolean isTreasureEnchantment() {
        return true;
    }

    public boolean canVillagerTrade() { return Config.EYESORE.get(); }

    public boolean canGenerateInLoot() { return Config.EYESORE.get(); }

    public boolean isAllowedOnBooks() {
        return Config.EYESORE.get();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.EYESORE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
