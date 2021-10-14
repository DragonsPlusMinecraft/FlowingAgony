package love.marblegate.flowingagony.effect.implicit;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.EffectRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ImplicitBaseEffect extends MobEffect {
    public ImplicitBaseEffect() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    public ImplicitBaseEffect(MobEffectCategory typeIn) {
        super(typeIn, 0);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }


    @Override
    public void initializeClient(Consumer<EffectRenderer> consumer) {
        consumer.accept(new EffectRenderer() {
            @Override
            public boolean shouldRender(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean shouldRenderInvText(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean shouldRenderHUD(MobEffectInstance effect) {
                return false;
            }

            @Override
            public void renderInventoryEffect(MobEffectInstance effect, EffectRenderingInventoryScreen<?> gui, PoseStack mStack, int x, int y, float z) {
            }

            @Override
            public void renderHUDEffect(MobEffectInstance effect, GuiComponent gui, PoseStack mStack, int x, int y, float z, float alpha) {
            }
        });
    }
}
