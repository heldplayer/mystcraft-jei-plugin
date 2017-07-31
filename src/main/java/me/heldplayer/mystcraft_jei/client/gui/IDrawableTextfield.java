package me.heldplayer.mystcraft_jei.client.gui;

import mezz.jei.api.gui.IDrawable;

import javax.annotation.Nonnull;

public interface IDrawableTextfield extends IDrawable {

    void setText(@Nonnull String text, boolean enabled);
}
