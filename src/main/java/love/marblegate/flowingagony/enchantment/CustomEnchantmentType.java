package love.marblegate.flowingagony.enchantment;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

import static net.minecraft.enchantment.EnchantmentType.BREAKABLE;
import static net.minecraft.enchantment.EnchantmentType.WEARABLE;

public class CustomEnchantmentType {
    public static EnchantmentType AXE_AND_SWORD = EnchantmentType.create("flowingagony:AXE_AND_SWORD", Item->{ return Item instanceof SwordItem || Item instanceof AxeItem;});
    public static EnchantmentType ALMOST_EVERYTHING = EnchantmentType.create("flowingagony:ALMOST_EVERYTHING",Item ->{
        return Item instanceof IVanishable || Block.getBlockFromItem(Item) instanceof IVanishable || BREAKABLE.canEnchantItem(Item) || WEARABLE.canEnchantItem(Item); });

    public static void addToItemGourp() {
        EnchantmentType[] TOOL_TYPES = new EnchantmentType[ItemGroup.TOOLS.getRelevantEnchantmentTypes().length + 2];
        for (int i = 0; i < ItemGroup.TOOLS.getRelevantEnchantmentTypes().length; i++) {
            TOOL_TYPES[i] = ItemGroup.TOOLS.getRelevantEnchantmentTypes()[i];
        }
        TOOL_TYPES[TOOL_TYPES.length - 2] = AXE_AND_SWORD;
        TOOL_TYPES[TOOL_TYPES.length - 1] = ALMOST_EVERYTHING;
        ItemGroup.TOOLS.setRelevantEnchantmentTypes(TOOL_TYPES);

        EnchantmentType[] COMBAT_TYPES = new EnchantmentType[ItemGroup.COMBAT.getRelevantEnchantmentTypes().length + 2];
        for (int i = 0; i < ItemGroup.COMBAT.getRelevantEnchantmentTypes().length; i++) {
            COMBAT_TYPES[i] = ItemGroup.COMBAT.getRelevantEnchantmentTypes()[i];
        }
        COMBAT_TYPES[COMBAT_TYPES.length - 2] = AXE_AND_SWORD;
        COMBAT_TYPES[COMBAT_TYPES.length - 1] = ALMOST_EVERYTHING;
        ItemGroup.COMBAT.setRelevantEnchantmentTypes(COMBAT_TYPES);

    }
}
