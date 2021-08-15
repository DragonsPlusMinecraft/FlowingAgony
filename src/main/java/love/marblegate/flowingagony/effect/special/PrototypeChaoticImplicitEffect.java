package love.marblegate.flowingagony.effect.special;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class PrototypeChaoticImplicitEffect extends HarmfulBlankImplicitEffect {
    public PrototypeChaoticImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * (double) (amplifier + 1);
    }
}
