package love.marblegate.flowingagony.enchantment.madeofsufferingcategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;

public class BurningPhobiaEnchantment extends Enchantment {
    public BurningPhobiaEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR_HEAD, EquipmentSlotTypeSet.HEAD);
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
        return super.canApplyTogether(p_77326_1_) && p_77326_1_
                != EnchantmentRegistry.drowning_phobia.get() && p_77326_1_
                != EnchantmentRegistry.prayer_of_pain.get();
    }

    @Override
    public boolean canVillagerTrade() { return Config.BURNING_PHOBIA.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.BURNING_PHOBIA.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.BURNING_PHOBIA.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.BURNING_PHOBIA.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
