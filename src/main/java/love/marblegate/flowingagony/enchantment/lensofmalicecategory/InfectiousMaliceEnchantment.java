package love.marblegate.flowingagony.enchantment.lensofmalicecategory;

import love.marblegate.flowingagony.config.Config;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentLevelUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class InfectiousMaliceEnchantment extends Enchantment{
    public InfectiousMaliceEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR, new EquipmentSlotType[]{EquipmentSlotType.LEGS,EquipmentSlotType.HEAD,EquipmentSlotType.CHEST,EquipmentSlotType.FEET});
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
        return super.canApplyTogether(p_77326_1_);
    }

    @Override
    public boolean canVillagerTrade() { return Config.INFECTIOUS_MALICE.get(); }

    @Override
    public boolean canGenerateInLoot() { return Config.INFECTIOUS_MALICE.get(); }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.INFECTIOUS_MALICE.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(Config.INFECTIOUS_MALICE.get())
            return super.canApplyAtEnchantingTable(stack);
        else
            return false;
    }
}
