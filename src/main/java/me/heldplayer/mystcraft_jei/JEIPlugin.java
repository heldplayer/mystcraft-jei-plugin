package me.heldplayer.mystcraft_jei;

import com.xcompwiz.mystcraft.api.MystObjects;
import me.heldplayer.mystcraft_jei.crafting.*;
import me.heldplayer.mystcraft_jei.subtypes.PageSubtypeInterpreter;
import me.heldplayer.mystcraft_jei.subtypes.UnlinkedLinkbookInterpreter;
import me.heldplayer.mystcraft_jei.util.Integration;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {
    public IIngredientRegistry ingredientRegistry;

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistry registry) {
        Item page = Integration.getMystItem(MystObjects.Items.page);
        if (page != null) {
            registry.registerSubtypeInterpreter(page, PageSubtypeInterpreter.INSTANCE);
        }

        Item unlinkedLinkbook = Integration.getMystItem(MystObjects.Items.linkbook_unlinked);
        if (unlinkedLinkbook != null) {
            registry.registerSubtypeInterpreter(unlinkedLinkbook, UnlinkedLinkbookInterpreter.INSTANCE);
        }

        Item portfolio = Integration.getMystItem(MystObjects.Items.portfolio);
        if (portfolio != null) {
            registry.useNbtForSubtypes(portfolio);
        }

        Item folder = Integration.getMystItem(MystObjects.Items.folder);
        if (folder != null) {
            registry.useNbtForSubtypes(folder);
        }
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new WritingDeskRecipeCategory(guiHelper),
                new InkMixerRecipeCategory(guiHelper),
                new BookBinderRecipeCategory(guiHelper)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public void register(@Nonnull IModRegistry registry) {
        this.ingredientRegistry = registry.getIngredientRegistry();

        IIngredientBlacklist ingredientBlacklist = registry.getJeiHelpers().getIngredientBlacklist();

        // Hide technical blocks
        Item starFissure = Integration.getMystItem(MystObjects.Blocks.star_fissure);
        if (starFissure != null) {
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(starFissure));
        }
        Item linkPortal = Integration.getMystItem(MystObjects.Blocks.portal);
        if (linkPortal != null) {
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(linkPortal));
        }

        // Hide descriptive book
        Item descriptiveBook = Integration.getMystItem(MystObjects.Items.descriptive_book);
        if (descriptiveBook != null) {
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(descriptiveBook));
        }

        // Hide empty page
        Item page = Integration.getMystItem(MystObjects.Items.page);
        if (page != null) {
            ingredientBlacklist.addIngredientToBlacklist(new ItemStack(page));
        }

        // Handle linking book recipe
        try {
            Class<? extends IRecipe> recipeLinkingbookClass = (Class<? extends IRecipe>) Class.forName("com.xcompwiz.mystcraft.data.RecipeLinkingbook");
            registry.handleRecipes(recipeLinkingbookClass, recipe -> new LinkingBookRecipeWrapper(), VanillaRecipeCategoryUid.CRAFTING);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Book binder recipes
        Item bookBinder = Integration.getMystItem(MystObjects.Blocks.bookbinder);
        if (bookBinder != null) {
            registry.addRecipeCatalyst(new ItemStack(bookBinder), MystcraftRecipeCategoryUid.BOOK_BINDER);
        }
        registry.addRecipes(BookBinderRecipes.getRecipes(), MystcraftRecipeCategoryUid.BOOK_BINDER);

        // Writing desk recipes
        Item writingDesk = Integration.getMystItem(MystObjects.Items.writing_desk);
        if (writingDesk != null) {
            registry.addRecipeCatalyst(new ItemStack(writingDesk, 1, 0), MystcraftRecipeCategoryUid.WRITING_DESK);
            registry.addRecipeCatalyst(new ItemStack(writingDesk, 1, 1), MystcraftRecipeCategoryUid.WRITING_DESK);
        }
        registry.addRecipes(WritingDeskRecipes.getRecipes(registry), MystcraftRecipeCategoryUid.WRITING_DESK);

        // Ink mixer recipes
        Item inkMixer = Integration.getMystItem(MystObjects.Blocks.inkmixer);
        if (inkMixer != null) {
            registry.addRecipeCatalyst(new ItemStack(inkMixer), MystcraftRecipeCategoryUid.INK_MIXER);
        }
        registry.addRecipes(InkMixerRecipes.getRecipes(registry), MystcraftRecipeCategoryUid.INK_MIXER);

        ItemDescriptions.initDescriptions();
        ItemDescriptions.setDescriptions(registry);

        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        MystcraftJEI.proxy.setRecipeIcon(guiHelper.createDrawable(Assets.ICONS_EXTRA, 0, 16, 16, 16, Assets.ICONS_EXTRA_WIDTH, Assets.ICONS_EXTRA_HEIGHT));

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            this.addRecipeClickAreas(registry);
        }
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    private void addRecipeClickAreas(IModRegistry registry) {
        try {
            registry.addRecipeClickArea((Class<? extends GuiContainer>) Class.forName("com.xcompwiz.mystcraft.client.gui.GuiBookBinder"), 151, 8, 17, 17, MystcraftRecipeCategoryUid.BOOK_BINDER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            registry.addRecipeClickArea((Class<? extends GuiContainer>) Class.forName("com.xcompwiz.mystcraft.client.gui.GuiWritingDesk"), 156 + 228, 45, 18, 34, MystcraftRecipeCategoryUid.WRITING_DESK);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            registry.addRecipeClickArea((Class<? extends GuiContainer>) Class.forName("com.xcompwiz.mystcraft.client.gui.GuiInkMixer"), 32, 36, 18, 18, MystcraftRecipeCategoryUid.INK_MIXER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime runtime) {
        // Add decay variants
        Item decay = Integration.getMystItem(MystObjects.Blocks.decay);
        if (decay != null && this.ingredientRegistry != null) {
            List<ItemStack> variants = new ArrayList<>();
            variants.add(new ItemStack(decay, 1, 1));
            variants.add(new ItemStack(decay, 1, 3));
            variants.add(new ItemStack(decay, 1, 4));
            variants.add(new ItemStack(decay, 1, 6));
            this.ingredientRegistry.addIngredientsAtRuntime(ItemStack.class, variants);
        }

        // Add unlinked linking books
        Item unlinkedLinkbook = Integration.getMystItem(MystObjects.Items.linkbook_unlinked);
        if (unlinkedLinkbook != null) {
            this.ingredientRegistry.addIngredientsAtRuntime(ItemStack.class, Integration.getAllLinkbooks());
        }
    }
}
