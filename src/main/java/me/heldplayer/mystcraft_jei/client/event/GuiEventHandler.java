package me.heldplayer.mystcraft_jei.client.event;

import me.heldplayer.mystcraft_jei.MystcraftJEI;
import me.heldplayer.mystcraft_jei.client.gui.DrawableRecipesButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiEventHandler {
    private final Class<? extends GuiContainer> INK_MIXER;
    private final Class<? extends GuiContainer> BOOK_BINDER;

    @SuppressWarnings("unchecked")
    public GuiEventHandler() {
        Class<? extends GuiContainer> clazz = null;
        try {
            clazz = (Class<? extends GuiContainer>) Class.forName("com.xcompwiz.mystcraft.client.gui.GuiInkMixer");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.INK_MIXER = clazz;

        clazz = null;
        try {
            clazz = (Class<? extends GuiContainer>) Class.forName("com.xcompwiz.mystcraft.client.gui.GuiBookBinder");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.BOOK_BINDER = clazz;
    }

    @SubscribeEvent
    public void onGuiScreenInitGuiPost(@Nonnull GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() != null) {
            if (this.INK_MIXER != null && this.INK_MIXER.isAssignableFrom(event.getGui().getClass())) {
                GuiContainer gui = (GuiContainer) event.getGui();
                GuiHelper.addRecipesButton(gui, 33, 37);
            } else if (this.BOOK_BINDER != null && this.BOOK_BINDER.isAssignableFrom(event.getGui().getClass())) {
                GuiContainer gui = (GuiContainer) event.getGui();
                GuiHelper.addRecipesButton(gui, 152, 9);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static class GuiHelper {
        private static final Field LABEL_LIST;

        static {
            LABEL_LIST = ReflectionHelper.findField(GuiScreen.class, "field_146293_o", "labelList");
        }

        @SuppressWarnings("unchecked")
        public static void addRecipesButton(@Nonnull GuiContainer screen, int posX, int posY) {
            try {
                List<GuiLabel> labelList = (List<GuiLabel>) LABEL_LIST.get(screen);
                labelList.removeIf(label -> label instanceof DrawableRecipesButton);
                labelList.add(new DrawableRecipesButton(screen.mc.fontRenderer, MystcraftJEI.proxy.getRecipeIcon(), -99, screen.getGuiLeft() + posX, screen.getGuiTop() + posY));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
