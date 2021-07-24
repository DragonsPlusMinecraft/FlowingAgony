package love.marblegate.flowingagony.enchantment.lastwish;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.enchantment.EquipmentSlotTypeSet;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentEnchantabilityCalculator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class GuidensRegretEnchantment extends Enchantment {
    public GuidensRegretEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, EquipmentSlotTypeSet.ALL);
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
                && p_77326_1_ != EnchantmentRegistry.MORIRS_LIFEBOUND.get()
                && p_77326_1_ != EnchantmentRegistry.MORIRS_DEATHWISH.get()
                && p_77326_1_ != Enchantments.MENDING;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() { return Config.GUIDENS_REGRET.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.GUIDENS_REGRET.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.GUIDENS_REGRET.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.GUIDENS_REGRET.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
