package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ISeeYouNowEnchantment extends Enchantment{
    public ISeeYouNowEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentType.ARMOR_HEAD, new EquipmentSlotType[]{EquipmentSlotType.HEAD});
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_77321_1_,true);
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return EnchantmentLevelUtil.get(this.getRarity(),this.getMaxLevel(),p_223551_1_,false);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canVillagerTrade() { return Config.I_SEE_YOU_NOW.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.I_SEE_YOU_NOW.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.I_SEE_YOU_NOW.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.I_SEE_YOU_NOW.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
