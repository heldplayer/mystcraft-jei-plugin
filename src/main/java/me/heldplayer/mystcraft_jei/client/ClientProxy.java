package me.heldplayer.mystcraft_jei.client;

import me.heldplayer.mystcraft_jei.CommonProxy;
import me.heldplayer.mystcraft_jei.client.event.GuiEventHandler;
import me.heldplayer.mystcraft_jei.client.gui.*;
import me.heldplayer.mystcraft_jei.crafting.InkMixerRecipeWrapper;
import me.heldplayer.mystcraft_jei.util.Entry;
import mezz.jei.api.gui.IDrawable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@Entry
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    private final GuiEventHandler guiEventHandler = new GuiEventHandler();

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(this.guiEventHandler);
    }

    @Override
    @Nonnull
    public IDrawableTextfield createTextField(int width, int height) {
        return new DrawableTextField(FMLClientHandler.instance().getClient().fontRenderer, width, height);
    }

    @Override
    @Nonnull
    public IDrawable createInkEyeOverlay(@Nonnull IDrawable other, @Nonnull InkMixerRecipeWrapper recipe) {
        return new DrawableInkEyeOverlay(other, recipe);
    }

    @Override
    @Nonnull
    public IDrawable createTankBackground(int width, int height) {
        return new DrawableTankBackground(width, height);
    }

    @Nonnull
    @Override
    public IDrawable createPageList(int width, int height) {
        return new DrawablePageList(width, height);
    }
}
