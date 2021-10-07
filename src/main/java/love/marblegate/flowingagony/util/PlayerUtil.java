package love.marblegate.flowingagony.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;

public class PlayerUtil {

    public static boolean hasHelmet(Player player) {
        return !player.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
    }

}
