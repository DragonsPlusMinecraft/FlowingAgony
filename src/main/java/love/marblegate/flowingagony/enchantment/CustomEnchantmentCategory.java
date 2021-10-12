package love.marblegate.flowingagony.enchantment;

import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Block;

public class CustomEnchantmentCategory {
    public static final EnchantmentCategory AXE_AND_SWORD = EnchantmentCategory.create("flowingagony:AXE_AND_SWORD", Item -> Item instanceof SwordItem || Item instanceof AxeItem);
    public static final EnchantmentCategory SWORD_ARMOR_AND_TOOL = EnchantmentCategory.create("flowingagony:SWORD_ARMOR_AND_TOOL", Item -> Item instanceof ArmorItem || Item instanceof TieredItem);
    public static final EnchantmentCategory BOW_AND_CROSSBOW = EnchantmentCategory.create("flowingagony:BOW_AND_CROSSBOW", Item -> Item instanceof ProjectileWeaponItem);
    public static final EnchantmentCategory PICKAXE = EnchantmentCategory.create("flowingagony:PICKAXE", Item -> Item instanceof PickaxeItem);
    public static final EnchantmentCategory ALMOST_EVERYTHING = EnchantmentCategory.create("flowingagony:ALMOST_EVERYTHING", Item -> Item instanceof Vanishable || Block.byItem(Item) instanceof Vanishable || EnchantmentCategory.BREAKABLE.canEnchant(Item) || EnchantmentCategory.WEARABLE.canEnchant(Item));

    public static void addToItemGroup() {
        EnchantmentCategory[] TOOL_TYPES = new EnchantmentCategory[CreativeModeTab.TAB_TOOLS.getEnchantmentCategories().length + 4];
        for (int i = 0; i < CreativeModeTab.TAB_TOOLS.getEnchantmentCategories().length; i++) {
            TOOL_TYPES[i] = CreativeModeTab.TAB_TOOLS.getEnchantmentCategories()[i];
        }
        TOOL_TYPES[TOOL_TYPES.length - 4] = PICKAXE;
        TOOL_TYPES[TOOL_TYPES.length - 3] = AXE_AND_SWORD;
        TOOL_TYPES[TOOL_TYPES.length - 2] = SWORD_ARMOR_AND_TOOL;
        TOOL_TYPES[TOOL_TYPES.length - 1] = ALMOST_EVERYTHING;
        CreativeModeTab.TAB_TOOLS.setEnchantmentCategories(TOOL_TYPES);

        EnchantmentCategory[] COMBAT_TYPES = new EnchantmentCategory[CreativeModeTab.TAB_COMBAT.getEnchantmentCategories().length + 4];
        for (int i = 0; i < CreativeModeTab.TAB_COMBAT.getEnchantmentCategories().length; i++) {
            COMBAT_TYPES[i] = CreativeModeTab.TAB_COMBAT.getEnchantmentCategories()[i];
        }
        COMBAT_TYPES[COMBAT_TYPES.length - 4] = AXE_AND_SWORD;
        COMBAT_TYPES[COMBAT_TYPES.length - 3] = SWORD_ARMOR_AND_TOOL;
        COMBAT_TYPES[COMBAT_TYPES.length - 2] = BOW_AND_CROSSBOW;
        COMBAT_TYPES[COMBAT_TYPES.length - 1] = ALMOST_EVERYTHING;
        CreativeModeTab.TAB_COMBAT.setEnchantmentCategories(COMBAT_TYPES);

    }
}
