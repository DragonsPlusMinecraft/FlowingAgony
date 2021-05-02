package love.marblegate.flowingagony.effect.special;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;

public class HatredBloodlineImplicitEffect extends ImplicitBaseEffect {
    public HatredBloodlineImplicitEffect() {
        super(EffectType.BENEFICIAL, 0);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * (double)(amplifier + 1);
    }
}
