package love.marblegate.flowingagony.enchantment.gloomyeracategory;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentCategory;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class NimbleFingerEnchantment extends Enchantment {
    public NimbleFingerEnchantment() {
        super(Rarity.RARE, CustomEnchantmentCategory.SWORD_ARMOR_AND_TOOL, EquipmentSlotTypeSet.ALL);
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
        if (Configuration.CompatibilitySetting.HYBRID_SERVER_USER.get()) return false;
        return Configuration.AcquirableSetting.NIMBLE_FINGER.get();
    }

    @Override
    public boolean isDiscoverable() {
        if (Configuration.CompatibilitySetting.HYBRID_SERVER_USER.get()) return false;
        return Configuration.AcquirableSetting.NIMBLE_FINGER.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        if (Configuration.CompatibilitySetting.HYBRID_SERVER_USER.get()) return false;
        return Configuration.AcquirableSetting.NIMBLE_FINGER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.CompatibilitySetting.HYBRID_SERVER_USER.get()) return false;
        if (Configuration.AcquirableSetting.NIMBLE_FINGER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
