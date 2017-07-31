package me.heldplayer.mystcraft_jei.client.gui;

import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class DrawablePageList extends Gui implements IDrawable {

    private final int width;
    private final int height;

    public DrawablePageList(int width, int height) {
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
        this.drawGradientRect(xOffset, yOffset, xOffset + this.getWidth(), yOffset + this.getHeight(), 0xAA000000, 0xAA000000);

        PageRenderer.renderInkPanel(minecraft, xOffset + 2, yOffset + 3, 0.0F, (this.getHeight() - 6) * 3 / 4, this.getHeight() - 6);

        this.drawGradientRect(xOffset, yOffset, xOffset + this.getHeight() / 5, yOffset + this.getHeight(), 0x33000000, 0x33000000);
        this.drawGradientRect(xOffset + this.getWidth() - this.getHeight() / 5, yOffset, xOffset + this.getWidth(), yOffset + this.getHeight(), 0x33000000, 0x33000000);
    }
}

