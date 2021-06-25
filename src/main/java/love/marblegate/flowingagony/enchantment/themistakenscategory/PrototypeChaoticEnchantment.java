package love.marblegate.flowingagony.enchantment.themistakenscategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class PrototypeChaoticEnchantment extends Enchantment {
    public PrototypeChaoticEnchantment(Rarity p_i46731_1_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, EnchantmentType.ARMOR_CHEST, p_i46731_3_);
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
        return 3;
    }

    @Override
    public boolean canVillagerTrade() { return Config.PROTOTYPE_CHAOTIC.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.PROTOTYPE_CHAOTIC.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.PROTOTYPE_CHAOTIC.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.PROTOTYPE_CHAOTIC.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
