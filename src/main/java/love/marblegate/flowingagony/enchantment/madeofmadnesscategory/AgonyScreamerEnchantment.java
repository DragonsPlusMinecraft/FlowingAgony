package love.marblegate.flowingagony.enchantment.madeofmadnesscategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class AgonyScreamerEnchantment extends Enchantment {
    public AgonyScreamerEnchantment(Rarity p_i46731_1_, EquipmentSlotType[] p_i46731_3_) {
        super(p_i46731_1_, EnchantmentType.BOW, p_i46731_3_);
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
    public boolean canApplyTogether(Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_ != EnchantmentRegistry.insane_poet_enchantment.get();
    }

    @Override
    public boolean canVillagerTrade() { return Config.AGONY_SCREAMER.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.AGONY_SCREAMER.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.AGONY_SCREAMER.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.AGONY_SCREAMER.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }

}
