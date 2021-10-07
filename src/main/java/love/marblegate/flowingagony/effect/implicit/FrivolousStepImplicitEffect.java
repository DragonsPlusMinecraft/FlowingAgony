package love.marblegate.flowingagony.effect.implicit;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FrivolousStepImplicitEffect extends BeneficialBlankImplicitEffect {
    public FrivolousStepImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + (0.15D * amplifier);
    }
}
