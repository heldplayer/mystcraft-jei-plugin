package me.heldplayer.mystcraft_jei.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class EmptyDrawable implements IDrawableTextfield {
    private final int width;
    private final int height;

    public EmptyDrawable(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setText(@Nonnull String text, boolean enabled) {
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(@Nonnull Minecraft minecraft) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
    }
}
