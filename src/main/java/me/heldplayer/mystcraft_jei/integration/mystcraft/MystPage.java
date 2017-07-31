package me.heldplayer.mystcraft_jei.integration.mystcraft;

import com.xcompwiz.mystcraft.api.hook.PageAPI;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MystPage {
    private static PageAPI api;

    protected static void setAPI(@Nonnull Object api) {
        MystPage.api = (PageAPI) api;
    }

    public static boolean isReady() {
        return api != null;
    }

    @Nullable
    public static String getPageSymbol(@Nonnull ItemStack itemStack) {
        return api.getPageSymbol(itemStack);
    }
}
