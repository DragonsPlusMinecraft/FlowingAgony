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

    public static boolean hasHelmet(PlayerEntity player){
        return !player.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty();
    }

}
