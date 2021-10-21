package love.marblegate.flowingagony.enchantment.gloomyera;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class NimbleFingerEnchantment extends Enchantment {
    public NimbleFingerEnchantment() {
        super(Rarity.RARE, CustomEnchantmentType.SWORD_ARMOR_AND_TOOL, EquipmentSlotTypeSet.ALL);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_77321_1_, true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentEnchantabilityCalculator.get(getRarity(), getMaxLevel(), p_223551_1_, false);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.NIMBLE_FINGER.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.NIMBLE_FINGER.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.NIMBLE_FINGER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.NIMBLE_FINGER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
