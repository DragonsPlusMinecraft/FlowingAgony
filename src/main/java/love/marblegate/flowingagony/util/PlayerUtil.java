package love.marblegate.flowingagony.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class PlayerUtil {

    public static boolean hasHelmet(Player player) {
        return !player.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
    }

}
