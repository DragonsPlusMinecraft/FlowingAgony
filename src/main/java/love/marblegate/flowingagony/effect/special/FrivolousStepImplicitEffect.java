package love.marblegate.flowingagony.effect.special;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;

public class FrivolousStepImplicitEffect extends BeneficialBlankImplicitEffect {
    public FrivolousStepImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + (0.02D * amplifier);
    }
}
