package love.marblegate.flowingagony.enchantment;

import net.minecraft.enchantment.Enchantment;

public class EnchantmentEnchantabilityCalculator {
    public static int get(Enchantment.Rarity rarity, int highestEnchantmentLevel, int currentLevel, boolean isMin) {
        switch (rarity.getWeight()) {
            //Rarity is VERY RARE
            case 1:
                if(isMin) return 30 - 4 * (highestEnchantmentLevel - currentLevel);
                else return 80 - 10 * (highestEnchantmentLevel - currentLevel);

            //Rarity is RARE
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
