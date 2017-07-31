package me.heldplayer.mystcraft_jei.crafting;

import com.google.common.collect.Lists;
import com.xcompwiz.mystcraft.api.MystObjects;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystItemFactory;
import me.heldplayer.mystcraft_jei.util.Integration;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class LinkingBookRecipeWrapper extends BlankRecipeWrapper {

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        Item result = Integration.getMystItem(MystObjects.Items.linkbook_unlinked);
        if (result == null) {
            return;
        }
        ItemStack pageStack = MystItemFactory.buildLinkPage();
        ItemStack resultStack = new ItemStack(result);
        NBTTagCompound tag = pageStack.getTagCompound();
        resultStack.setTagCompound(tag == null ? null : tag.copy());

        ingredients.setInputs(ItemStack.class, Lists.newArrayList(new ItemStack(Items.LEATHER), pageStack));
        ingredients.setOutput(ItemStack.class, resultStack);
    }
}
