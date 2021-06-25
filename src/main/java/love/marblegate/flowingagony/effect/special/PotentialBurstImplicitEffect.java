package love.marblegate.flowingagony.effect.special;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;

public class PotentialBurstImplicitEffect extends ImplicitBaseEffect {
    public PotentialBurstImplicitEffect() {
        super(EffectType.BENEFICIAL, 0);
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + (0.02D * amplifier);
    }
}
