package me.heldplayer.mystcraft_jei.integration.mystcraft;

import com.xcompwiz.mystcraft.api.hook.LinkPropertyAPI;
import com.xcompwiz.mystcraft.api.util.ColorGradient;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class MystLinkProperty {
    private static LinkPropertyAPI api;

    protected static void setAPI(@Nonnull Object api) {
        MystLinkProperty.api = (LinkPropertyAPI) api;
    }

    public static boolean isReady() {
        return api != null;
    }

    @Nonnull
    public static ColorGradient getPropertiesGradient(@Nonnull Map<String, Float> map) {
        return api.getPropertiesGradient(map);
    }

    @Nullable
    public static Map<String, Float> getPropertiesForItem(@Nonnull ItemStack itemStack) {
        return api.getPropertiesForItem(itemStack);
    }
}
