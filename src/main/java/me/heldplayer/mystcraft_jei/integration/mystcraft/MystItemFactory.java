package me.heldplayer.mystcraft_jei.integration.mystcraft;

import com.xcompwiz.mystcraft.api.hook.ItemFactory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MystItemFactory {
    private static ItemFactory api;

    protected static void setAPI(@Nonnull Object api) {
        MystItemFactory.api = (ItemFactory) api;
    }

    public static boolean isReady() {
        return api != null;
    }

    @Nonnull
    public static ItemStack buildSymbolPage(@Nonnull String s) {
        return api.buildSymbolPage(s);
    }

    @Nonnull
    public static ItemStack buildLinkPage(@Nonnull String... strings) {
        return api.buildLinkPage(strings);
    }
}
