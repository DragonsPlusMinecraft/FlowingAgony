package love.marblegate.flowingagony.util;

import net.minecraft.enchantment.Enchantment;

public class EnchantmentLevelUtil {
    public static int get(Enchantment.Rarity rarity, int highestEnchantmentLevel, int currentLevel, boolean isMin) {
        switch (rarity.getWeight()) {
            //Rarity is VERY RARE

            //We define "StandardMin" as "When currentLevel reaches maximum, the minimal enchantability should be".
            //StandardMin is 30,

            //We define "StandardMax" as "When currentLevel reaches maximum, the maximal enchantability should be".
            //StandardMax is 80,
            case 1:
                if(isMin) return 30 - 4 * (highestEnchantmentLevel - currentLevel);
                else return 80 - 10 * (highestEnchantmentLevel - currentLevel);

            //Rarity is RARE

            //We define "StandardMin" as "When currentLevel reaches maximum, the minimal enchantability should be".
            //StandardMin is 20,

            //We define "StandardMax" as "When currentLevel reaches maximum, the maximal enchantability should be".
            //StandardMax is 70,
            case 2:
                if(isMin) return 20 - 3 * (highestEnchantmentLevel - currentLevel);
                else return 70 - 9 * (highestEnchantmentLevel - currentLevel);

            //Rarity is UNCOMMON
            case 5:
                if(isMin) return 10 - (highestEnchantmentLevel - currentLevel);
                else return 60 - 8 * (highestEnchantmentLevel - currentLevel);

            //Rarity is COMMON
            case 10:
                if(isMin) return 1 + currentLevel;
                else return 51 - 6 * (highestEnchantmentLevel - currentLevel);
        }
        return 1;
    }
}
