package love.marblegate.flowingagony.effect;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EnviousBeingEffect extends Effect {
    public EnviousBeingEffect() {
        super(EffectType.BENEFICIAL, 0);
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + amplifier;
    }
}
