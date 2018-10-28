package me.heldplayer.mystcraft_jei.crafting;

import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import me.heldplayer.mystcraft_jei.client.gui.IDrawableTextfield;
import me.heldplayer.mystcraft_jei.client.gui.PageRenderer;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystPage;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystSymbol;
import me.heldplayer.mystcraft_jei.util.Integration;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WritingDeskRecipes {

    private WritingDeskRecipes() {
    }

    public static Collection<AwareRecipeWrapper> getRecipes(@Nonnull IModRegistry modRegistry) {
        Set<String> validInks = Integration.getValidInks();
        int inkcost = Integration.getInkcost();

        return Stream.concat(
                modRegistry.getIngredientRegistry().getAllIngredients(VanillaTypes.ITEM).stream()
                        .map((stack) -> Pair.of(stack, FluidUtil.getFluidContained(stack)))
                        .filter((stacks) -> stacks.getRight() != null)
                        .filter((stacks) -> stacks.getRight().amount <= 1000)
                        .filter((stacks) -> validInks.contains(stacks.getRight().getFluid().getName()))
                        .map((stacks) -> new InkFill(stacks.getLeft(), stacks.getRight())),

                Stream.of(new SymbolWrite(validInks.stream()
                        .map((name) -> FluidRegistry.getFluidStack(name, inkcost))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                ))
        ).collect(Collectors.toList());
    }

    public abstract static class AwareRecipeWrapper implements IRecipeWrapper {
        protected IGuiIngredient<ItemStack> inputSlot;
        protected IDrawableTextfield textField;

        public void setInputSlot(IGuiIngredient<ItemStack> inputSlot) {
            this.inputSlot = inputSlot;
        }

        public void setTextField(IDrawableTextfield textField) {
            this.textField = textField;
        }

        @Override
        public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            this.textField.draw(minecraft, 22, 55);
        }
    }

    private static class InkFill extends AwareRecipeWrapper {
        private final ItemStack inputItem;
        private final ItemStack outputItem;
        private final FluidStack fluid;

        public InkFill(@Nonnull ItemStack input, @Nonnull FluidStack fluid) {
            this.inputItem = input.copy();
            this.outputItem = input.getItem().getContainerItem(input);

            this.fluid = fluid.copy();
        }

        @Override
        public void getIngredients(@Nonnull IIngredients ingredients) {
            List<List<ItemStack>> inputStacks = new ArrayList<>();
            inputStacks.add(Collections.emptyList());
            inputStacks.add(Collections.singletonList(this.inputItem));

            List<List<ItemStack>> outputStacks = new ArrayList<>();
            outputStacks.add(Collections.emptyList());
            outputStacks.add(this.outputItem.isEmpty() ? Collections.emptyList() : Collections.singletonList(this.outputItem));

            List<List<FluidStack>> outputFluids = new ArrayList<>();
            outputFluids.add(Collections.singletonList(this.fluid));

            ingredients.setInputLists(VanillaTypes.ITEM, inputStacks);
            ingredients.setOutputLists(VanillaTypes.FLUID, outputFluids);
            ingredients.setOutputLists(VanillaTypes.ITEM, outputStacks);
        }

        @Override
        public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            this.textField.setText("", false);

            super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        }
    }

    private static class SymbolWrite extends AwareRecipeWrapper {
        private final List<FluidStack> validFluids;

        private SymbolWrite(List<FluidStack> validFluids) {
            this.validFluids = validFluids;
        }

        @Override
        public void getIngredients(@Nonnull IIngredients ingredients) {
            List<List<ItemStack>> inputStacks = new ArrayList<>();
            inputStacks.add(Collections.singletonList(new ItemStack(Items.PAPER)));

            List<List<ItemStack>> outputStacks = new ArrayList<>();
            outputStacks.add(Integration.gatherAllSymbolStacks());

            List<List<FluidStack>> inputFluids = new ArrayList<>();
            inputFluids.add(this.validFluids);

            ingredients.setInputLists(VanillaTypes.ITEM, inputStacks);
            ingredients.setInputLists(VanillaTypes.FLUID, inputFluids);
            ingredients.setOutputLists(VanillaTypes.ITEM, outputStacks);
        }

        @Override
        public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
            this.textField.setText("", false);

            ItemStack ingredient = this.inputSlot.getDisplayedIngredient();
            if (ingredient != null) {
                ResourceLocation symbol = MystPage.getPageSymbol(ingredient);
                if (symbol != null) {
                    IAgeSymbol ageSymbol = MystSymbol.getSymbol(symbol);
                    if (ageSymbol != null) {
                        PageRenderer.renderPage(minecraft, symbol, 26.0F, 0.0F, 0.0F, 37.0F, 50.0F);
                        this.textField.setText(ageSymbol.getLocalizedName(), false);
                    }
                }
            }

            super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        }

        @Override
        @Nonnull
        public List<String> getTooltipStrings(int mouseX, int mouseY) {
            // Draw tooltip for enlarged page preview
            if (mouseX >= 26 && mouseX < 63 && mouseY >= 0 && mouseY < 50) {
                ItemStack ingredient = this.inputSlot.getDisplayedIngredient();
                if (ingredient != null) {
                    ResourceLocation symbol = MystPage.getPageSymbol(ingredient);
                    if (symbol != null) {
                        IAgeSymbol ageSymbol = MystSymbol.getSymbol(symbol);
                        if (ageSymbol != null) {
                            return Collections.singletonList(ageSymbol.getLocalizedName());
                        }
                    }
                }
            }

            // Draw tooltip for text field
            /*
            if (mouseX >= 21 && mouseX < 120 && mouseY >= 54 && mouseY < 68) {
                ItemStack ingredient = this.inputSlot.getDisplayedIngredient();
                if (ingredient != null) {
                    String symbol = MystPage.api.getPageSymbol(ingredient);
                    if (symbol != null) {
                        IAgeSymbol ageSymbol = MystSymbol.api.getSymbol(symbol);
                        if (ageSymbol != null) {
                            return Collections.singletonList(Util.translate("mystcraft-jei.writing_desk.page.name"));
                        }
                    }
                }
            }
            */
            return Collections.emptyList();
        }
    }
}
