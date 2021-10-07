package love.marblegate.flowingagony.effect.implicit;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class PotentialBurstImplicitEffect extends BeneficialBlankImplicitEffect {
    public PotentialBurstImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * (1 + amplifier);
    }
}
