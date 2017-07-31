package me.heldplayer.mystcraft_jei.client.gui;

import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class DrawableTankBackground extends Gui implements IDrawable {

    private final int width;
    private final int height;

    public DrawableTankBackground(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft) {
        this.draw(minecraft, 0, 0);
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
        this.drawGradientRect(xOffset, yOffset, xOffset + this.getWidth(), yOffset + this.getHeight(), 0x99000000, 0x99000000);
        this.drawGradientRect(xOffset + 1, yOffset + 1, xOffset + this.getWidth() - 1, yOffset + this.getHeight() - 1, 0xFFCCCCEE, 0xFF666699);
    }
}

