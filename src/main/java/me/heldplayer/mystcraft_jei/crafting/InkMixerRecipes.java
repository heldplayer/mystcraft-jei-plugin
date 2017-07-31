package me.heldplayer.mystcraft_jei.crafting;

import me.heldplayer.mystcraft_jei.util.Integration;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InkMixerRecipes {
    private InkMixerRecipes() {
    }

    public static Collection<IRecipeWrapper> getRecipes(@Nonnull IModRegistry modRegistry) {
        List<Object> inputs = Integration.getInkMixerInputs();
        Set<String> validInks = Integration.getValidInks();

        return Stream.concat(
                modRegistry.getIngredientRegistry().getIngredients(ItemStack.class).stream()
                        .map((stack) -> Pair.of(stack, FluidUtil.getFluidContained(stack)))
                        .filter((stacks) -> stacks.getRight() != null)
                        .filter((stacks) -> stacks.getRight().amount == 1000)
                        .filter((stacks) -> validInks.contains(stacks.getRight().getFluid().getName()))
                        .map((stacks) -> new InkFill(stacks.getLeft(), stacks.getRight())),

                inputs.stream()
                        .map(input -> {
                            if (input instanceof ItemStack) {
                                return new InkMixerRecipeWrapper((ItemStack) input);
                            } else if (input instanceof String) {
                                return new InkMixerRecipeWrapper((String) input);
                            } else if (input instanceof Item) {
                                return new InkMixerRecipeWrapper((Item) input);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .filter(InkMixerRecipeWrapper::notEmpty)
        ).collect(Collectors.toList());
    }

    private static class InkFill extends BlankRecipeWrapper {

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
            inputStacks.add(Collections.singletonList(this.inputItem));

            List<List<ItemStack>> outputStacks = new ArrayList<>();
            outputStacks.add(this.outputItem.isEmpty() ? Collections.emptyList() : Collections.singletonList(this.outputItem));

            List<List<FluidStack>> outputFluids = new ArrayList<>();
            outputFluids.add(Collections.singletonList(this.fluid));

            ingredients.setInputLists(ItemStack.class, inputStacks);
            ingredients.setOutputLists(FluidStack.class, outputFluids);
            ingredients.setOutputLists(ItemStack.class, outputStacks);
        }
    }
}
