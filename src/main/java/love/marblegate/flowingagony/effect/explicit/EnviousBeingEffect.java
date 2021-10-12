package love.marblegate.flowingagony.effect.explicit;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class EnviousBeingEffect extends MobEffect {
    public EnviousBeingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() + amplifier;
    }
}
