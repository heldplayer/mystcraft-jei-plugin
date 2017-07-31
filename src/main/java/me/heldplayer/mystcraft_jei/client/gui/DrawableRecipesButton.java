package me.heldplayer.mystcraft_jei.client.gui;

import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class DrawableRecipesButton extends GuiLabel {
    private final IDrawable icon;

    public DrawableRecipesButton(@Nonnull FontRenderer fontRenderer, @Nonnull IDrawable icon, int id, int posX, int posY) {
        super(fontRenderer, id, posX, posY, icon.getWidth(), icon.getHeight(), 0);
        this.icon = icon;
    }

    @Override
    public void drawLabel(@Nonnull Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            //this.drawGradientRect(this.x - 1, this.y - 1, this.x + this.icon.getWidth() + 1, this.y + this.icon.getHeight() + 1, 0xFFFF0000, 0xFFFF0000);
            this.icon.draw(mc, this.x, this.y);
        }
    }
}

