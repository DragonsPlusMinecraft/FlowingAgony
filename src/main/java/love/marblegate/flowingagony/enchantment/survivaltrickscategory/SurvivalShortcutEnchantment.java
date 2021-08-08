package love.marblegate.flowingagony.enchantment.survivaltrickscategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class SurvivalShortcutEnchantment extends Enchantment {
    public SurvivalShortcutEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.SURVIVAL_RUSE.get() && p_77326_1_ != EnchantmentRegistry.NECESSARY_EVIL.get();
    }

    @Override
    public boolean canVillagerTrade() { return Configuration.SURVIVAL_SHORTCUT.get(); }

    @Override
    public boolean canGenerateInLoot() { return Configuration.SURVIVAL_SHORTCUT.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.SURVIVAL_SHORTCUT.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Configuration.SURVIVAL_SHORTCUT.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
