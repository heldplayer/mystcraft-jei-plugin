package me.heldplayer.mystcraft_jei.crafting;

import me.heldplayer.mystcraft_jei.Assets;
import me.heldplayer.mystcraft_jei.MystcraftJEI;
import me.heldplayer.mystcraft_jei.Util;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class InkMixerRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    public static final int SLOT_TANK_INPUT = 0;
    public static final int SLOT_TANK_OUTPUT = 1;
    public static final int SLOT_PAPER = 2;
    public static final int SLOT_RESULT = 3;
    public static final int SLOT_INGREDIENT = 4;
    public static final int TANK_INK = 0;

    public static final int width = 166;
    public static final int height = 76;

    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final IDrawable iconDrop;
    private final String title;

    public InkMixerRecipeCategory(@Nonnull IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("mystcraft", "gui/inkmixer.png");
        this.background = guiHelper.createDrawable(location, 5, 11, width, height);
        this.tankOverlay = guiHelper.createDrawable(location, 54, 16, 67, 66);
        this.iconDrop = guiHelper.drawableBuilder(Assets.ICONS_EXTRA, 0, 0, 18, 10).setTextureSize(Assets.ICONS_EXTRA_WIDTH, Assets.ICONS_EXTRA_HEIGHT).build();
        this.title = Util.translate("mystcraft-jei.category.ink_mixer");
    }

    @Override
    @Nonnull
    public String getUid() {
        return MystcraftRecipeCategoryUid.INK_MIXER;
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
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(SLOT_TANK_INPUT, true, 2, 15);
        guiItemStacks.init(SLOT_TANK_OUTPUT, false, 146, 15);
        guiItemStacks.init(SLOT_PAPER, true, 2, 36);
        guiItemStacks.init(SLOT_RESULT, false, 146, 36);
        guiItemStacks.init(SLOT_INGREDIENT, true, 27, 25);
        guiItemStacks.set(ingredients);

        IDrawable tankOverlay = this.tankOverlay;
        if (wrapper instanceof InkMixerRecipeWrapper) {
            InkMixerRecipeWrapper recipe = (InkMixerRecipeWrapper) wrapper;
            tankOverlay = MystcraftJEI.proxy.createInkEyeOverlay(tankOverlay, recipe);
            recipe.setDropIcon(this.iconDrop);
        }

        if (ingredients.getInputs(VanillaTypes.FLUID).size() > 0) {
            guiFluidStacks.init(TANK_INK, true, 49, 5, 67, 66, 1000, false, tankOverlay);
        }
        if (ingredients.getOutputs(VanillaTypes.FLUID).size() > 0) {
            guiFluidStacks.init(TANK_INK, false, 49, 5, 67, 66, 1000, false, tankOverlay);
        }

        guiFluidStacks.set(ingredients);
    }
}
