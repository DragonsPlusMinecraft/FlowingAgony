package love.marblegate.flowingagony.enchantment.madeofsufferingcategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class DestructionWorshipEnchantment extends Enchantment {
    public DestructionWorshipEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentType.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
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
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_)
                && p_77326_1_ != EnchantmentRegistry.constrained_heart.get()
                && p_77326_1_ != EnchantmentRegistry.piercing_fever.get();
    }

    @Override
    public boolean canVillagerTrade() { return Config.PIERCING_FEVER.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.PIERCING_FEVER.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.PIERCING_FEVER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.PIERCING_FEVER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
