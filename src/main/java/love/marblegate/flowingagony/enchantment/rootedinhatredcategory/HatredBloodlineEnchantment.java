package love.marblegate.flowingagony.enchantment.rootedinhatredcategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class HatredBloodlineEnchantment extends Enchantment{
    public HatredBloodlineEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentType.ARMOR, new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET});
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
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() { return Config.HATRED_BLOODLINE.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.HATRED_BLOODLINE.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.HATRED_BLOODLINE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.HATRED_BLOODLINE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
