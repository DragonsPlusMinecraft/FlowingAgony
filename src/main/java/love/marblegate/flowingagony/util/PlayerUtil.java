package love.marblegate.flowingagony.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class PlayerUtil {

    public static boolean hasHelmet(PlayerEntity player) {
        return !player.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty();
    }

}
