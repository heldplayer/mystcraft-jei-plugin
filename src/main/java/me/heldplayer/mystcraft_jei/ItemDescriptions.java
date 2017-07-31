package me.heldplayer.mystcraft_jei;

import com.xcompwiz.mystcraft.api.MystObjects;
import me.heldplayer.mystcraft_jei.util.Integration;
import mezz.jei.api.IModRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class ItemDescriptions {

    private static final Map<List<Pair<String, Integer>>, String[]> DESCRIPTIONS = new LinkedHashMap<>();

    public static void initDescriptions() {
        ItemDescriptions.DESCRIPTIONS.clear();

        // Decay blocks
        {
            List<Pair<String, Integer>> list = new ArrayList<>();
            list.add(Pair.of(MystObjects.Blocks.decay, 0));
            list.add(Pair.of(MystObjects.Blocks.decay, 1));
            //list.add(Pair.of(MystObjects.Blocks.decay, 2));
            list.add(Pair.of(MystObjects.Blocks.decay, 3));
            list.add(Pair.of(MystObjects.Blocks.decay, 4));
            //list.add(Pair.of(MystObjects.Blocks.decay, 5));
            list.add(Pair.of(MystObjects.Blocks.decay, 6));
            ItemDescriptions.DESCRIPTIONS.put(list,
                    new String[]{"mystcraft-jei.description.decay.1", "mystcraft-jei.description.decay.2"});
        }
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 0)), // Black decay
                new String[]{"mystcraft-jei.description.decay.black.1", "mystcraft-jei.description.decay.black.2",
                        "mystcraft-jei.description.decay.black.3", "mystcraft-jei.description.decay.black.4"});
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 1)), // Red decay
                new String[]{"mystcraft-jei.description.decay.red.1", "mystcraft-jei.description.decay.red.2"});
        /*
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 2)), // Green decay
                new String[]{"mystcraft-jei.description.decay.green.1"});
         */
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 3)), // Blue decay
                new String[]{"mystcraft-jei.description.decay.blue.1", "mystcraft-jei.description.decay.blue.2"});
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 4)), // Purple decay
                new String[]{"mystcraft-jei.description.decay.purple.1", "mystcraft-jei.description.decay.purple.2"});
        /*
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 5)), // Yellow decay
                new String[]{"mystcraft-jei.description.decay.yellow.1"});
         */
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.decay, 6)), // White decay
                new String[]{"mystcraft-jei.description.decay.white.1", "mystcraft-jei.description.decay.white.2",
                        "mystcraft-jei.description.decay.white.3", "mystcraft-jei.description.decay.white.4"});

        // Lectern
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.book_lectern, 0)),
                new String[]{"mystcraft-jei.description.lectern.1", "mystcraft-jei.description.lectern.2"});

        // Bookstand
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.bookstand, 0)),
                new String[]{"mystcraft-jei.description.bookstand.1", "mystcraft-jei.description.bookstand.2"});

        // Crystal
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.crystal, 0)),
                new String[]{"mystcraft-jei.description.crystal.1", "mystcraft-jei.description.crystal.2"});

        // Book receptacle
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.crystal_receptacle, 0)),
                new String[]{"mystcraft-jei.description.crystal_receptacle.1", "mystcraft-jei.description.crystal_receptacle.2",
                        "mystcraft-jei.description.crystal_receptacle.3"});

        // Ink mixer
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.inkmixer, 0)),
                new String[]{"mystcraft-jei.description.ink_mixer.1", "mystcraft-jei.description.ink_mixer.2"});

        // Writing desk + backboard
        {
            List<Pair<String, Integer>> list = new ArrayList<>();
            list.add(Pair.of(MystObjects.Items.writing_desk, 0));
            list.add(Pair.of(MystObjects.Items.writing_desk, 1));
            ItemDescriptions.DESCRIPTIONS.put(list,
                    new String[]{"mystcraft-jei.description.writing_desk.1", "mystcraft-jei.description.writing_desk.2",
                            "mystcraft-jei.description.writing_desk.3"});
        }

        // Book binder
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.bookbinder, 0)),
                new String[]{"mystcraft-jei.description.book_binder.1", "mystcraft-jei.description.book_binder.2",
                        "mystcraft-jei.description.book_binder.3", "mystcraft-jei.description.book_binder.4"});

        // Link modifier
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Blocks.link_modifer, 0)),
                new String[]{"mystcraft-jei.description.link_modifer.1", "mystcraft-jei.description.link_modifer.2",
                        "mystcraft-jei.description.link_modifer.3"});

        // Ink vial
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Items.inkvial, 0)),
                new String[]{"mystcraft-jei.description.ink_vial.1", "mystcraft-jei.description.ink_vial.2"});

        // Unlinked link book
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Items.linkbook_unlinked, 0)),
                new String[]{"mystcraft-jei.description.unlinked_linkbook.1", "mystcraft-jei.description.unlinked_linkbook.2"});

        // Linking book
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Items.linkbook, 0)),
                new String[]{"mystcraft-jei.description.linked_linkbook.1", "mystcraft-jei.description.linked_linkbook.2"});

        // Sealed notebook
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Items.booster, 0)),
                new String[]{"mystcraft-jei.description.sealed_notebook.1", "mystcraft-jei.description.sealed_notebook.2"});

        // Collation folder
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Items.folder, 0)),
                new String[]{"mystcraft-jei.description.folder.1", "mystcraft-jei.description.folder.2"});

        // Symbol portfolio
        ItemDescriptions.DESCRIPTIONS.put(Collections.singletonList(Pair.of(MystObjects.Items.portfolio, 0)),
                new String[]{"mystcraft-jei.description.portfolio.1", "mystcraft-jei.description.portfolio.2"});
    }

    public static void setDescriptions(@Nonnull IModRegistry registry) {
        ItemDescriptions.DESCRIPTIONS.forEach((stacks, descriptions) -> {
            // Basic items/blocks
            registry.addIngredientInfo(stacks.stream()
                    .map((stack) -> Pair.of(Integration.getMystItem(stack.getLeft()), stack.getRight()))
                    .filter((stack) -> stack.getLeft() != null && stack.getLeft() != Items.AIR)
                    .map((stack) -> new ItemStack(stack.getLeft(), 1, stack.getRight()))
                    .collect(Collectors.toList()), ItemStack.class, descriptions);

            // Link panels
            // Symbol pages
        });
    }

    private ItemDescriptions() {
    }
}
