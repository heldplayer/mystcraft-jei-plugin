package me.heldplayer.mystcraft_jei.client.gui;

import com.xcompwiz.mystcraft.api.util.Color;
import me.heldplayer.mystcraft_jei.crafting.InkMixerRecipeWrapper;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystRender;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class DrawableInkEyeOverlay extends Gui implements IDrawable {

    private final IDrawable other;
    private final InkMixerRecipeWrapper recipe;

    public DrawableInkEyeOverlay(IDrawable other, InkMixerRecipeWrapper recipe) {
        this.other = other;
        this.recipe = recipe;
    }

    @Override
    public int getWidth() {
        return this.other.getWidth();
    }

    @Override
    public int getHeight() {
        return this.other.getHeight();
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft) {
        Color color = this.recipe.getCurrentEffectColor();
        if (color != null) {
            int iColor = color.asInt();
            this.drawGradientRect(0, 0, this.getWidth(), this.getHeight(), 0x40000000 | iColor, 0xB0000000 | iColor);

            MystRender.drawColorEye(82.5F, 37.5F, 0.0F, 20.0F, color);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        this.other.draw(minecraft);
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
        Color color = this.recipe.getCurrentEffectColor();
        if (color != null) {
            int iColor = color.asInt();
            this.drawGradientRect(xOffset, yOffset, xOffset + this.getWidth(), yOffset + this.getHeight(), 0x40000000 | iColor, 0xB0000000 | iColor);

            MystRender.drawColorEye(xOffset + this.getWidth() / 2.0F + 0.5F, yOffset + this.getHeight() / 2.0F, 0.0F, 20.0F, color);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        this.other.draw(minecraft, xOffset, yOffset);
    }
}

