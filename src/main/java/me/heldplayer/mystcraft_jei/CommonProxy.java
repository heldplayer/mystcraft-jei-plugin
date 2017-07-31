package me.heldplayer.mystcraft_jei;

import me.heldplayer.mystcraft_jei.client.gui.EmptyDrawable;
import me.heldplayer.mystcraft_jei.client.gui.IDrawableTextfield;
import me.heldplayer.mystcraft_jei.crafting.InkMixerRecipeWrapper;
import mezz.jei.api.gui.IDrawable;

import javax.annotation.Nonnull;

public class CommonProxy {
    private IDrawable recipeIcon = new EmptyDrawable(16, 16);

    public void registerEventHandlers() {
    }

    @Nonnull
    public IDrawableTextfield createTextField(int width, int height) {
        return new EmptyDrawable(width, height);
    }

    @Nonnull
    public IDrawable createInkEyeOverlay(@Nonnull IDrawable other, @Nonnull InkMixerRecipeWrapper recipe) {
        return new EmptyDrawable(other.getWidth(), other.getHeight());
    }

    @Nonnull
    public IDrawable createTankBackground(int width, int height) {
        return new EmptyDrawable(width, height);
    }

    @Nonnull
    public IDrawable createPageList(int width, int height) {
        return new EmptyDrawable(width, height);
    }

    public void setRecipeIcon(@Nonnull IDrawable icon) {
        this.recipeIcon = icon;
    }

    @Nonnull
    public IDrawable getRecipeIcon() {
        return this.recipeIcon;
    }
}
