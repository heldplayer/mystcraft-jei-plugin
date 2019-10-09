package me.heldplayer.mystcraft_jei.crafting;

import me.heldplayer.mystcraft_jei.MystcraftJEI;
import me.heldplayer.mystcraft_jei.Util;
import me.heldplayer.mystcraft_jei.client.gui.IDrawableTextfield;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class WritingDeskRecipeCategory extends BlankRecipeCategory<WritingDeskRecipes.AwareRecipeWrapper> {
    public static final int SLOT_PAPER = 0;
    public static final int SLOT_TANK_INPUT = 1;
    public static final int SLOT_RESULT = 2;
    public static final int SLOT_TANK_OUTPUT = 3;
    public static final int TANK_INK = 0;

    public static final int width = 162;
    public static final int height = 70;

    private final IDrawable background;
    private final String title;

    private final IDrawable tankBackground;
    private final IDrawableTextfield textField;

    public WritingDeskRecipeCategory(@Nonnull IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("mystcraft", "gui/writingdesk.png");
        this.background = guiHelper.createDrawable(location, 7, 7, width, height);
        this.title = Util.translate("mystcraft-jei.category.writing_desk");
        this.tankBackground = MystcraftJEI.proxy.createTankBackground(16, 70);
        this.textField = MystcraftJEI.proxy.createTextField(97, 12);
    }

    @Override
    @Nonnull
    public String getUid() {
        return MystcraftRecipeCategoryUid.WRITING_DESK;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return this.title;
    }

    @Override
    @Nonnull
    public String getModName() {
        return "Mystcraft";
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull WritingDeskRecipes.AwareRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(SLOT_PAPER, true, 0, 0);
        guiItemStacks.init(SLOT_TANK_INPUT, true, 144, 0);
        guiItemStacks.init(SLOT_RESULT, false, 0, 52);
        guiItemStacks.init(SLOT_TANK_OUTPUT, false, 144, 52);
        guiItemStacks.set(ingredients);

        if (ingredients.getInputs(VanillaTypes.FLUID).size() > 0) {
            guiFluidStacks.init(TANK_INK, true, 126, 1, 14, 68, 1000, false, null);
        }
        if (ingredients.getOutputs(VanillaTypes.FLUID).size() > 0) {
            guiFluidStacks.init(TANK_INK, false, 126, 1, 14, 68, 1000, false, null);
        }

        guiFluidStacks.set(ingredients);

        wrapper.setInputSlot(guiItemStacks.getGuiIngredients().get(SLOT_RESULT));
        wrapper.setTextField(this.textField);
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        this.tankBackground.draw(minecraft, 125, 0);
    }
}
