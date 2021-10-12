package love.marblegate.flowingagony.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EnchantmentUtil {
    public static int isItemEnchanted(ItemStack itemStack, Enchantment enchantment) {
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
        return enchantList.containsKey(enchantment) ? 1 : 0;
    }

    public static int isPlayerArmorEnchanted(Player player, Enchantment enchantment, ArmorEncCalOp mode) {
        if (mode == ArmorEncCalOp.GENERAL) {
            return isPlayerArmorEnchanted_general(player, enchantment);
        } else if (mode == ArmorEncCalOp.TOTAL_PIECE) {
            return isPlayerArmorEnchanted_totalPiece(player, enchantment);
        } else if (mode == ArmorEncCalOp.TOTAL_LEVEL) {
            return isPlayerArmorEnchanted_totalLevel(player, enchantment);
        } else {
            return isPlayerArmorEnchanted_highestLevel(player, enchantment);
        }
    }

    public static int isPlayerItemEnchanted(Player player, Enchantment enchantment, EquipmentSlot slotIn, ItemEncCalOp mode) {
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(player.getItemBySlot(slotIn));
        if (mode == ItemEncCalOp.GENERAL) {
            return enchantList.containsKey(enchantment) ? 1 : 0;
        } else {
            return enchantList.getOrDefault(enchantment, 0);
        }
    }

    public static List<ItemStack> getItemStackWithEnchantment(Player player, Enchantment enchantment) {
        List<ItemStack> items = new ArrayList<>();
        for (EquipmentSlot type : EquipmentSlot.values()) {
            if (isPlayerItemEnchanted(player, enchantment, type, ItemEncCalOp.GENERAL) == 1) {
                items.add(player.getItemBySlot(type));
            }
        }
        return items;
    }

    static int isPlayerArmorEnchanted_general(Player player, Enchantment enchantment) {
        for (ItemStack itemStack : player.getArmorSlots()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
            if (enchantList.containsKey(enchantment)) {
                return 1;
            }
        }
        return 0;
    }

    static int isPlayerArmorEnchanted_totalPiece(Player player, Enchantment enchantment) {
        Iterator<ItemStack> armor = player.getArmorSlots().iterator();
        int count = 0;
        while (armor.hasNext()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(armor.next());
            if (enchantList.containsKey(enchantment)) {
                count++;
            }
        }
        return count;
    }

    static int isPlayerArmorEnchanted_totalLevel(Player player, Enchantment enchantment) {
        Iterator<ItemStack> armor = player.getArmorSlots().iterator();
        int count = 0;
        while (armor.hasNext()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(armor.next());
            if (enchantList.containsKey(enchantment)) {
                count += enchantList.get(enchantment);
            }
        }
        return count;
    }

    static int isPlayerArmorEnchanted_highestLevel(Player player, Enchantment enchantment) {
        Iterator<ItemStack> armor = player.getArmorSlots().iterator();
        int count = 0;
        while (armor.hasNext()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(armor.next());
            if (enchantList.containsKey(enchantment)) {
                if (enchantList.get(enchantment) > count) count = enchantList.get(enchantment);
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
