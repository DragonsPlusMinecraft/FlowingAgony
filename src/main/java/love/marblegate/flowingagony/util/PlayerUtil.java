package love.marblegate.flowingagony.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PlayerUtil {
    public static boolean isPlayerArmorEnchanted(PlayerEntity player, Enchantment enchantment){
        for (ItemStack itemStack : player.getArmorInventoryList()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
            if (enchantList.containsKey(enchantment)) {
                return true;
            }
        }
        return false;
    }

    public static int isPlayerArmorEnchantedWithEnchantmentLevel(PlayerEntity player, Enchantment enchantment){
        for (ItemStack itemStack : player.getArmorInventoryList()) {
            Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(itemStack);
            if (enchantList.containsKey(enchantment)) {
                return enchantList.get(enchantment);
            }
        }
        return 0;
    }

    public static boolean isPlayerSpecificSlotEnchanted(PlayerEntity player, Enchantment enchantment, EquipmentSlotType slotIn){
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(player.getItemStackFromSlot(slotIn));
        return enchantList.containsKey(enchantment);
    }

    public static int isPlayerSpecificSlotWithEnchantmentLevel(PlayerEntity player, Enchantment enchantment,EquipmentSlotType slotIn){
        Map<Enchantment, Integer> enchantList = EnchantmentHelper.getEnchantments(player.getItemStackFromSlot(slotIn));
        return enchantList.getOrDefault(enchantment, 0);
    }

    public static int getTotalLevelPlayerArmorEnchantedSameEnchantment(PlayerEntity player, Enchantment enchantment){
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

    public static int getTotalPiecePlayerArmorEnchantedSameEnchantment(PlayerEntity player, Enchantment enchantment){
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

    public static int getHighestLevelPlayerArmorEnchantedSameEnchantment(PlayerEntity player, Enchantment enchantment){
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

    public static List<ItemStack> getItemStackWithCertainEnchantment(PlayerEntity player, Enchantment enchantment){
        List<ItemStack> items = new ArrayList<>();
        for(EquipmentSlotType type : EquipmentSlotType.values()){
            if(isPlayerSpecificSlotEnchanted(player,enchantment,type)){
                items.add(player.getItemStackFromSlot(type));
            }
        }
        return items;
    }

    public static List<LivingEntity> getTargetList(PlayerEntity player, float radius, float height, Predicate<LivingEntity> predicate){
        AxisAlignedBB aabb = new AxisAlignedBB(player.getPosition().getX()-radius,player.getPosition().getY()-height,player.getPosition().getZ()-radius,player.getPosition().getX()+radius,player.getPosition().getY()+height,player.getPosition().getZ()+radius);
        List<LivingEntity> entities = player.world.getEntitiesWithinAABB(LivingEntity.class,aabb,predicate);
        entities.remove(player);
        return entities;
    }

    public static List<LivingEntity> getTargetListUnderSameType(PlayerEntity player, float radius, float height, LivingEntity sourceEntity){
        AxisAlignedBB aabb = new AxisAlignedBB(player.getPosition().getX()-radius,player.getPosition().getY()-height,player.getPosition().getZ()-radius,player.getPosition().getX()+radius,player.getPosition().getY()+height,player.getPosition().getZ()+radius);
        List<LivingEntity> entities = player.world.getEntitiesWithinAABB(LivingEntity.class,aabb);
        List<LivingEntity> qualifiedEntities = new ArrayList<>();
        for (LivingEntity entity : entities){
            if((entity.getClass() == sourceEntity.getClass())){
                qualifiedEntities.add(entity);
            }
        }
        entities.remove(player);
        return qualifiedEntities;
    }

    public static boolean hasHelmet(PlayerEntity player){
        return !player.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty();
    }

}
