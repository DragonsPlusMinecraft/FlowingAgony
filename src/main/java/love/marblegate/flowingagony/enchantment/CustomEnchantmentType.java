package love.marblegate.flowingagony.enchantment;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.item.*;

import static net.minecraft.enchantment.EnchantmentType.BREAKABLE;
import static net.minecraft.enchantment.EnchantmentType.WEARABLE;

public class CustomEnchantmentType {
    public static final EnchantmentType AXE_AND_SWORD = EnchantmentType.create("flowingagony:AXE_AND_SWORD", Item-> Item instanceof SwordItem || Item instanceof AxeItem);
    public static final EnchantmentType BOW_AND_CROSSBOW = EnchantmentType.create("flowingagony:BOW_AND_CROSSBOW", Item-> Item instanceof BowItem || Item instanceof CrossbowItem);
    public static final EnchantmentType ALMOST_EVERYTHING = EnchantmentType.create("flowingagony:ALMOST_EVERYTHING", Item -> Item instanceof IVanishable || Block.getBlockFromItem(Item) instanceof IVanishable || BREAKABLE.canEnchantItem(Item) || WEARABLE.canEnchantItem(Item));

    public static void addToItemGourp() {
        EnchantmentType[] TOOL_TYPES = new EnchantmentType[ItemGroup.TOOLS.getRelevantEnchantmentTypes().length + 2];
        for (int i = 0; i < ItemGroup.TOOLS.getRelevantEnchantmentTypes().length; i++) {
            TOOL_TYPES[i] = ItemGroup.TOOLS.getRelevantEnchantmentTypes()[i];
        }
        TOOL_TYPES[TOOL_TYPES.length - 2] = AXE_AND_SWORD;
        TOOL_TYPES[TOOL_TYPES.length - 1] = ALMOST_EVERYTHING;
        ItemGroup.TOOLS.setRelevantEnchantmentTypes(TOOL_TYPES);

        EnchantmentType[] COMBAT_TYPES = new EnchantmentType[ItemGroup.COMBAT.getRelevantEnchantmentTypes().length + 3];
        for (int i = 0; i < ItemGroup.COMBAT.getRelevantEnchantmentTypes().length; i++) {
            COMBAT_TYPES[i] = ItemGroup.COMBAT.getRelevantEnchantmentTypes()[i];
        }
        COMBAT_TYPES[COMBAT_TYPES.length - 3] = AXE_AND_SWORD;
        COMBAT_TYPES[COMBAT_TYPES.length - 2] = BOW_AND_CROSSBOW;
        COMBAT_TYPES[COMBAT_TYPES.length - 1] = ALMOST_EVERYTHING;
        ItemGroup.COMBAT.setRelevantEnchantmentTypes(COMBAT_TYPES);

    }
}
