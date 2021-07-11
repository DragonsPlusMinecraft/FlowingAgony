package love.marblegate.flowingagony.enchantment.survivaltrickscategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class SurvivalRuseEnchantment extends Enchantment {
    public SurvivalRuseEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.survival_shortcut.get() && p_77326_1_ != EnchantmentRegistry.necessary_evil.get();
    }

    @Override
    public boolean canVillagerTrade() { return Config.SURVIVAL_RUSE.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.SURVIVAL_RUSE.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.SURVIVAL_RUSE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.SURVIVAL_RUSE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
