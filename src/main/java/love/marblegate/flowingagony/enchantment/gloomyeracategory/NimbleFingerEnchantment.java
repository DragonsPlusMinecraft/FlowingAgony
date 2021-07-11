package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class NimbleFingerEnchantment extends Enchantment {
    public NimbleFingerEnchantment() {
        super(Rarity.RARE, CustomEnchantmentType.SWORD_ARMOR_AND_TOOL , EquipmentSlotTypeSet.ALL);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(this.getRarity(),this.getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(this.getRarity(),this.getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canVillagerTrade() {
        if(Config.HYBRID_SERVER_USER.get()) return false;
        return Config.NIMBLE_FINGER.get(); }

    @Override
    public boolean canGenerateInLoot() {
        if(Config.HYBRID_SERVER_USER.get()) return false;
        return Config.NIMBLE_FINGER.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        if(Config.HYBRID_SERVER_USER.get()) return false;
        return Config.NIMBLE_FINGER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.HYBRID_SERVER_USER.get()) return false;
        if(Config.NIMBLE_FINGER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
