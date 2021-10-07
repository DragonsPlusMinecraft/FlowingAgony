package love.marblegate.flowingagony.effect.implicit;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FreshRevengeImplicitEffect extends BeneficialBlankImplicitEffect {
    public FreshRevengeImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + amplifier + 1;
    }
}
