package me.heldplayer.mystcraft_jei.client.gui;

import me.heldplayer.mystcraft_jei.Assets;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class PageRenderer {
    private PageRenderer() {
    }

    @SideOnly(Side.CLIENT)
    public static void renderPage(@Nonnull Minecraft minecraft, @Nonnull ResourceLocation symbol, float posX, float posY, float zLevel, float width, float height) {
        minecraft.getTextureManager().bindTexture(Assets.BOOK_PAGE_LEFT);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(posX, posY, width, height, zLevel, 0.609375F, 0.0F, 0.7265625F, 0.15625F);

        MystRender.drawSymbol(posX + 0.5F, posY + (height + 1.0F - width) / 2.0F, 0.0F, width - 1.0F, symbol);
    }

    @SideOnly(Side.CLIENT)
    public static void renderInkPanel(@Nonnull Minecraft minecraft, float posX, float posY, float zLevel, float width, float height) {
        minecraft.getTextureManager().bindTexture(Assets.BOOK_PAGE_LEFT);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(posX, posY, width, height, zLevel, 0.609375F, 0.0F, 0.7265625F, 0.15625F);

        drawGradientRect(posX + width * 0.15F, posY + height * 0.15F, posX + width * 0.85F, posY + height * 0.5F, zLevel, 0xff000000, 0xff000000);
    }

    private static void drawGradientRect(float left, float top, float right, float bottom, float zLevel, int startColor, int endColor) {
        float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
        float startRed = (float) (startColor >> 16 & 255) / 255.0F;
        float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
        float startBlue = (float) (startColor & 255) / 255.0F;
        float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
        float endRed = (float) (endColor >> 16 & 255) / 255.0F;
        float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
        float endBlue = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(right, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        vertexbuffer.pos(left, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        vertexbuffer.pos(left, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        vertexbuffer.pos(right, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    @SideOnly(Side.CLIENT)
    private static void drawTexturedModalRect(float posX, float posY, float width, float height, float zLevel, float uStart, float vStart, float uEnd, float vEnd) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(posX, posY + height, zLevel).tex(uStart, vEnd).endVertex();
        vertexbuffer.pos(posX + width, posY + height, zLevel).tex(uEnd, vEnd).endVertex();
        vertexbuffer.pos(posX + width, posY, zLevel).tex(uEnd, vStart).endVertex();
        vertexbuffer.pos(posX, posY, zLevel).tex(uStart, vStart).endVertex();
        tessellator.draw();
    }
}

