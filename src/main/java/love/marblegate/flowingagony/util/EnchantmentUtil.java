package love.marblegate.flowingagony.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EnchantmentUtil {
    public static int isItemEnchanted(ItemStack itemStack,Enchantment enchantment){
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
        return enchantList.containsKey(enchantment)?1:0;
    }

    public static int isPlayerArmorEnchanted(PlayerEntity player, Enchantment enchantment, ArmorEncCalOp mode){
        if(mode == ArmorEncCalOp.GENERAL){
            return isPlayerArmorEnchanted_general(player,enchantment);
        } else if(mode == ArmorEncCalOp.TOTAL_PIECE){
            return isPlayerArmorEnchanted_totalPiece(player,enchantment);
        } else if(mode == ArmorEncCalOp.TOTAL_LEVEL){
            return isPlayerArmorEnchanted_totalLevel(player,enchantment);
        } else {
            return isPlayerArmorEnchanted_highestLevel(player,enchantment);
        }
    }

    public static int isPlayerItemEnchanted(PlayerEntity player, Enchantment enchantment, EquipmentSlotType slotIn, ItemEncCalOp mode){
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(player.getItemStackFromSlot(slotIn));
        if(mode == ItemEncCalOp.GENERAL){
            return enchantList.containsKey(enchantment) ? 1 : 0;
        } else {
            return enchantList.getOrDefault(enchantment, 0);
        }
    }

    public static List<ItemStack> getItemStackWithEnchantment(PlayerEntity player, Enchantment enchantment){
        List<ItemStack> items = new ArrayList<>();
        for(EquipmentSlotType type : EquipmentSlotType.values()){
            if(isPlayerItemEnchanted(player,enchantment,type, ItemEncCalOp.GENERAL)==1){
                items.add(player.getItemStackFromSlot(type));
            }
        }
        return items;
    }

    static int isPlayerArmorEnchanted_general(PlayerEntity player, Enchantment enchantment){
        for (ItemStack itemStack : player.getArmorInventoryList()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
            if (enchantList.containsKey(enchantment)) {
                return 1;
            }
        }
        return 0;
    }

    static int isPlayerArmorEnchanted_totalPiece(PlayerEntity player, Enchantment enchantment){
        Iterator<ItemStack> armor = player.getArmorInventoryList().iterator();
        int count = 0;
        while(armor.hasNext()){
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(armor.next());
            if(enchantList.containsKey(enchantment)){
                count ++;
            }
        }
        return count;
    }

    static int isPlayerArmorEnchanted_totalLevel(PlayerEntity player, Enchantment enchantment){
        Iterator<ItemStack> armor = player.getArmorInventoryList().iterator();
        int count = 0;
        while(armor.hasNext()){
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(armor.next());
            if(enchantList.containsKey(enchantment)){
                count += enchantList.get(enchantment);
            }
        }
        return count;
    }

    static int isPlayerArmorEnchanted_highestLevel(PlayerEntity player, Enchantment enchantment){
        Iterator<ItemStack> armor = player.getArmorInventoryList().iterator();
        int count = 0;
        while(armor.hasNext()){
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(armor.next());
            if(enchantList.containsKey(enchantment)){
                if(enchantList.get(enchantment)>count) count = enchantList.get(enchantment);
            }
        }
        return count;
    }

    public enum ArmorEncCalOp {
        GENERAL,
        TOTAL_PIECE,
        TOTAL_LEVEL,
        HIGHEST_LEVEL
    }

    public enum ItemEncCalOp {
        GENERAL,
        TOTAL_LEVEL
    }
}
