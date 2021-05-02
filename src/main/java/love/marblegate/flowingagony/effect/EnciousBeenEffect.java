package love.marblegate.flowingagony.effect;

import love.marblegate.flowingagony.effect.special.ImplicitBaseEffect;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EnciousBeenEffect extends Effect {
    public EnciousBeenEffect() {
        super(EffectType.BENEFICIAL, 0);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + amplifier;
    }
}
