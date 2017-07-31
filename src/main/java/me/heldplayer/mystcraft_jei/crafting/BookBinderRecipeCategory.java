package me.heldplayer.mystcraft_jei.crafting;

import me.heldplayer.mystcraft_jei.MystcraftJEI;
import me.heldplayer.mystcraft_jei.Util;
import me.heldplayer.mystcraft_jei.client.gui.IDrawableTextfield;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class BookBinderRecipeCategory extends BlankRecipeCategory<BookBinderRecipes.AwareRecipeWrapper> {
    public static final int SLOT_LEATHER = 0;
    public static final int SLOT_RESULT = 1;

    private final IDrawable background;
    private final String title;

    private final IDrawableTextfield textField;

    public BookBinderRecipeCategory(@Nonnull IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("mystcraft", "gui/pagebinder.png");
        this.background = guiHelper.createDrawable(location, 7, 7, 162, 70);
        this.title = Util.translate("mystcraft-jei.category.book_binder");
        this.textField = MystcraftJEI.proxy.createTextField(114, 12);
    }

    @Override
    @Nonnull
    public String getUid() {
        return MystcraftRecipeCategoryUid.BOOK_BINDER;
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
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull BookBinderRecipes.AwareRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(SLOT_LEATHER, true, 0, 19);
        guiItemStacks.init(SLOT_RESULT, false, 144, 19);
        guiItemStacks.set(ingredients);

        wrapper.setTextField(this.textField);
    }
}
