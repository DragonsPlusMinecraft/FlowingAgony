package love.marblegate.flowingagony.enchantment.madeofmadnesscategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class ShockTherapyEnchantment extends Enchantment {
    public ShockTherapyEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentType.WEAPON, EquipmentSlotTypeSet.MAIN_HAND);
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
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_)
                && p_77326_1_ != EnchantmentRegistry.paper_brain.get()
                && p_77326_1_ != Enchantments.SHARPNESS
                && p_77326_1_ != Enchantments.BANE_OF_ARTHROPODS
                && p_77326_1_ != Enchantments.SMITE;
    }

    @Override
    public boolean canVillagerTrade() { return Config.SHOCK_THERAPY.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.SHOCK_THERAPY.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.SHOCK_THERAPY.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.SHOCK_THERAPY.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
