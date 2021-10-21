package love.marblegate.flowingagony.enchantment.diceoffraud;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.enchantment.CustomEnchantmentType;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class TricksterEnchantment extends Enchantment {
    public TricksterEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, CustomEnchantmentType.AXE_AND_SWORD, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
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
        return 2;
    }

    @Override
    public boolean canVillagerTrade() {
        return Configuration.AcquirableSetting.TRICKSTER.get();
    }

    @Override
    public boolean canGenerateInLoot() {
        return Configuration.AcquirableSetting.TRICKSTER.get();
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Configuration.AcquirableSetting.TRICKSTER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if (Configuration.AcquirableSetting.TRICKSTER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
