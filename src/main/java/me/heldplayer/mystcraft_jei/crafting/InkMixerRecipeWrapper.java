package me.heldplayer.mystcraft_jei.crafting;

import com.xcompwiz.mystcraft.api.MystObjects;
import com.xcompwiz.mystcraft.api.util.Color;
import com.xcompwiz.mystcraft.api.util.ColorGradient;
import me.heldplayer.mystcraft_jei.InkMixerOutput;
import me.heldplayer.mystcraft_jei.Util;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystItemFactory;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InkMixerRecipeWrapper extends BlankRecipeWrapper {

    @Nonnull
    private final List<ItemStack> ingredients;
    @Nonnull
    private final String[] modifiers;
    @Nullable
    private final ColorGradient gradient;

    private IDrawable iconDrop;

    private int frame;

    public InkMixerRecipeWrapper(@Nonnull ItemStack stack) {
        this.ingredients = Collections.singletonList(stack);

        InkMixerOutput result = InkMixerOutput.getOutputFor(stack);
        if (result == null) {
            this.modifiers = new String[0];
            this.gradient = null;
        } else {
            this.modifiers = result.getModifiers();
            this.gradient = result.getGradient();
        }
    }

    public InkMixerRecipeWrapper(@Nonnull String oredict) {
        this.ingredients = OreDictionary.getOres(oredict);

        InkMixerOutput result = this.ingredients.size() > 0 ? InkMixerOutput.getOutputFor(this.ingredients.get(0)) : null;
        if (result == null) {
            this.modifiers = new String[0];
            this.gradient = null;
        } else {
            this.modifiers = result.getModifiers();
            this.gradient = result.getGradient();
        }
    }

    public InkMixerRecipeWrapper(@Nonnull Item item) {
        this.ingredients = Collections.singletonList(new ItemStack(item));

        InkMixerOutput result = InkMixerOutput.getOutputFor(this.ingredients.get(0));
        if (result == null) {
            this.modifiers = new String[0];
            this.gradient = null;
        } else {
            this.modifiers = result.getModifiers();
            this.gradient = result.getGradient();
        }
    }

    public boolean notEmpty() {
        return !this.ingredients.isEmpty();
    }

    public void setDropIcon(IDrawable icon) {
        this.iconDrop = icon;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (!GuiScreen.isShiftKeyDown()) {
            this.frame++;
        }

        if (this.iconDrop != null) {
            this.iconDrop.draw(minecraft, 27, 45);
        }
    }

    @Nullable
    public Color getCurrentEffectColor() {
        return this.gradient == null ? null : this.gradient.getColor((float) this.frame / 300.0F);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        List<List<ItemStack>> inputStacks = new ArrayList<>();
        inputStacks.add(Collections.emptyList());
        inputStacks.add(Collections.singletonList(new ItemStack(Items.PAPER)));
        inputStacks.add(this.ingredients);

        List<List<ItemStack>> outputStacks = new ArrayList<>();
        outputStacks.add(Collections.emptyList());
        outputStacks.add(Collections.singletonList(MystItemFactory.buildLinkPage(this.modifiers)));
        // Extra output stacks that are not shown, for when there's more than one modifier on an item
        if (this.modifiers.length > 1) {
            outputStacks.add(Arrays.stream(this.modifiers).map(MystItemFactory::buildLinkPage).collect(Collectors.toList()));
        }

        List<List<FluidStack>> inputFluids = new ArrayList<>();
        inputFluids.add(Collections.singletonList(FluidRegistry.getFluidStack(MystObjects.Fluids.black_ink, 1000)));

        ingredients.setInputLists(ItemStack.class, inputStacks);
        ingredients.setInputLists(FluidStack.class, inputFluids);
        ingredients.setOutputLists(ItemStack.class, outputStacks);
    }

    @Override
    @Nonnull
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        // Draw tooltip for dropping item in the puddle
        if (mouseX >= 27 && mouseX < 45 && mouseY >= 45 && mouseY < 55) {
            return Collections.singletonList(Util.translate("mystcraft-jei.ink_mixer.drop"));
        }

        return Collections.emptyList();
    }
}
