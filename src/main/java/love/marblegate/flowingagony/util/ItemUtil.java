package love.marblegate.flowingagony.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ItemUtil {
    public static boolean isItemEnchanted(ItemStack itemStack,Enchantment enchantment){
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
        if(enchantList.containsKey(enchantment)) return true;
        else return false;
    }
}
