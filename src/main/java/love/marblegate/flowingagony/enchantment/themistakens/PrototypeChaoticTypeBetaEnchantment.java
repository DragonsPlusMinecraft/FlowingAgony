package love.marblegate.flowingagony.enchantment.themistakens;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PrototypeChaoticTypeBetaEnchantment extends Enchantment {
    public PrototypeChaoticTypeBetaEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlotTypeSet.CHEST);
    }

    @Override
    public int getMinCost(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_77321_1_, true);
    }

    @Override
    public int getMaxCost(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_223551_1_, false);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTradeable() {
        return Configuration.AcquirableSetting.PROTOTYPE_CHAOTIC_TYPE_BETA.get();
    }

    @Override
    public boolean isDiscoverable() {
        return Configuration.AcquirableSetting.PROTOTYPE_CHAOTIC_TYPE_BETA.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.PROTOTYPE_CHAOTIC_TYPE_BETA.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.PROTOTYPE_CHAOTIC_TYPE_BETA.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
