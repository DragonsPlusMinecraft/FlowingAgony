package love.marblegate.flowingagony.util;

import net.minecraft.enchantment.Enchantment;

public class EnchantmentLevelUtil {
    public static int get(Enchantment.Rarity rarity, int highestEnchantmentLevel, int currentLevel, boolean isMin) {
        switch (rarity.getWeight()) {
            case 1:
                if (highestEnchantmentLevel == 1) {
                    if (isMin) {
                        return 25;
                    } else {
                        return 50;
                    }
                } else if (highestEnchantmentLevel == 2) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 21;
                        } else {
                            return 25;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 30;
                        } else {
                            return 50;
                        }
                    }
                } else if (highestEnchantmentLevel == 3) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 21;
                        } else if (currentLevel == 2) {
                            return 23;
                        } else {
                            return 25;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 30;
                        } else if (currentLevel == 2) {
                            return 35;
                        } else {
                            return 50;
                        }
                    }
                }
                break;
            case 2:
                if (highestEnchantmentLevel == 1) {
                    if (isMin) {
                        return 23;
                    } else {
                        return 50;
                    }
                } else if (highestEnchantmentLevel == 2) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 19;
                        } else {
                            return 23;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 30;
                        } else {
                            return 50;
                        }
                    }
                } else if (highestEnchantmentLevel == 3) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 19;
                        } else if (currentLevel == 2) {
                            return 21;
                        } else {
                            return 23;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 27;
                        } else if (currentLevel == 2) {
                            return 32;
                        } else {
                            return 50;
                        }
                    }
                }
                break;
            case 5:
                if (highestEnchantmentLevel == 1) {
                    if (isMin) {
                        return 19;
                    } else {
                        return 50;
                    }
                } else if (highestEnchantmentLevel == 2) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 15;
                        } else {
                            return 19;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 27;
                        } else {
                            return 50;
                        }
                    }
                } else if (highestEnchantmentLevel == 3) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 15;
                        } else if (currentLevel == 2) {
                            return 17;
                        } else {
                            return 19;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 23;
                        } else if (currentLevel == 2) {
                            return 28;
                        } else {
                            return 50;
                        }
                    }
                }
                break;
            case 10:
                if (highestEnchantmentLevel == 1) {
                    if (isMin) {
                        return 14;
                    } else {
                        return 50;
                    }
                } else if (highestEnchantmentLevel == 2) {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 10;
                        } else {
                            return 14;
                        }
                    } else {
                        if (currentLevel == 1) {
                            return 22;
                        } else {
                            return 50;
                        }
                    }
                } else {
                    if (isMin) {
                        if (currentLevel == 1) {
                            return 10;
                        } else if (currentLevel == 2) {
                            return 12;
                        } else {
                            return 14;
                        }
                    } else if (highestEnchantmentLevel == 3) {
                        if (currentLevel == 1) {
                            return 18;
                        } else if (currentLevel == 2) {
                            return 23;
                        } else {
                            return 50;
                        }
                    }
                }
                break;
        }
        return 1;
    }
}
