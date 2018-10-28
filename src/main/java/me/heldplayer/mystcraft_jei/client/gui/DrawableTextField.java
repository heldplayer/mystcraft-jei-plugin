package me.heldplayer.mystcraft_jei.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class DrawableTextField extends Gui implements IDrawableTextfield {

    private final int width;
    private final int height;
    private final GuiTextField textField;

    public DrawableTextField(@Nonnull FontRenderer fontRenderer, int width, int height) {
        this.width = width;
        this.height = height;
        this.textField = new GuiTextField(0, fontRenderer, 0, 0, width, height);
    }

    @Override
    public void setText(@Nonnull String text, boolean enabled) {
        this.textField.setText(text);
        this.textField.setCursorPosition(0);
        this.textField.setEnabled(enabled);
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
        this.textField.x = xOffset;
        this.textField.y = yOffset;
        this.textField.drawTextBox();
    }
}

