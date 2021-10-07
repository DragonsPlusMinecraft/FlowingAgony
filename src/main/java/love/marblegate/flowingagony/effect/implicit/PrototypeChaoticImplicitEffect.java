package love.marblegate.flowingagony.effect.implicit;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class PrototypeChaoticImplicitEffect extends HarmfulBlankImplicitEffect {
    public PrototypeChaoticImplicitEffect() {
        super();
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * (double) (amplifier + 1);
    }
}
