package me.heldplayer.mystcraft_jei.crafting;

import com.xcompwiz.mystcraft.api.MystObjects;
import me.heldplayer.mystcraft_jei.MystcraftJEI;
import me.heldplayer.mystcraft_jei.client.gui.IDrawableTextfield;
import me.heldplayer.mystcraft_jei.util.Integration;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BookBinderRecipes {

    private BookBinderRecipes() {
    }

    public static Collection<AwareRecipeWrapper> getRecipes() {
        return Collections.singletonList(new AgeWrite());
    }

    public abstract static class AwareRecipeWrapper implements IRecipeWrapper {
        protected IDrawableTextfield textField;

        public void setTextField(IDrawableTextfield textField) {
            this.textField = textField;
        }

        @Override
        public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            this.textField.draw(minecraft, 1, 3);
        }
    }

    private static class AgeWrite extends AwareRecipeWrapper {
        private final IDrawable pageList = MystcraftJEI.proxy.createPageList(162, 40);

        @Override
        public void getIngredients(@Nonnull IIngredients ingredients) {
            List<List<ItemStack>> inputStacks = new ArrayList<>();
            inputStacks.add(Collections.singletonList(new ItemStack(Items.LEATHER)));

            // Extra input stacks that are not shown in a slot, but are in the "recipe"
            inputStacks.add(Integration.gatherAllSymbolStacks());

            List<List<ItemStack>> outputStacks = new ArrayList<>();
            Item descriptiveBook = Integration.getMystItem(MystObjects.Items.descriptive_book);
            outputStacks.add(descriptiveBook == null ? Collections.emptyList() : Collections.singletonList(new ItemStack(descriptiveBook)));

            ingredients.setInputLists(VanillaTypes.ITEM, inputStacks);
            ingredients.setOutputLists(VanillaTypes.ITEM, outputStacks);
        }

        @Override
        public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            this.textField.setText("Age Name", true);

            super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

            this.pageList.draw(minecraft, 0, 38);
        }
    }
}
