package love.marblegate.flowingagony.effect.special;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class FreshRevengeImplicitEffect extends BeneficialBlankImplicitEffect {
    public FreshRevengeImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + amplifier + 1;
    }
}
