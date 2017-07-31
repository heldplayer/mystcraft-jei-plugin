package me.heldplayer.mystcraft_jei.integration.mystcraft;

import com.xcompwiz.mystcraft.api.hook.RenderAPI;
import com.xcompwiz.mystcraft.api.util.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class MystRender {
    private static RenderAPI api;

    protected static void setAPI(@Nonnull Object api) {
        MystRender.api = (RenderAPI) api;
    }

    public static boolean isReady() {
        return api != null;
    }

    @SideOnly(Side.CLIENT)
    public static void drawSymbol(float posX, float posY, float zLevel, float radius, @Nonnull String identifier) {
        api.drawSymbol(posX, posY, zLevel, radius, identifier);
    }

    @SideOnly(Side.CLIENT)
    public static void drawColorEye(float posX, float posY, float zLevel, float radius, @Nonnull Color color) {
        api.drawColorEye(posX, posY, zLevel, radius, color);
    }
}
