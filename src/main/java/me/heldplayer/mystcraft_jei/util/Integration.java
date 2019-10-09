package me.heldplayer.mystcraft_jei.util;

import com.xcompwiz.mystcraft.api.MystObjects;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import com.xcompwiz.mystcraft.api.util.Color;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystItemFactory;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystSymbol;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ReportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class Integration {

    private static List<ItemStack> allLinkbooks = Collections.emptyList();
    private static Field itemstack_bindings = null;
    private static Field oredict_bindings = null;
    private static Field itemId_bindings = null;
    private static Field validInks = null;
    private static Field inkcost = null;

    private Integration() {
    }

    public static void initialize() {
        Integration.prepareLinkbooks();
        Integration.getMethodsAndFields();
    }

    @Nullable
    public static Item getMystItem(@Nonnull String name) {
        Item item = Item.getByNameOrId(MystObjects.MystcraftModId + ":" + name);
        if (item == null || item == Items.AIR) {
            return null;
        }
        return item;
    }

    @SuppressWarnings("unchecked")
    private static void prepareLinkbooks() {
        Map<String, Color> colormap;

        try {
            Class<?> inkEffectsClass = Class.forName("com.xcompwiz.mystcraft.data.InkEffects");
            Field colormapField = inkEffectsClass.getDeclaredField("colormap");
            colormapField.setAccessible(true);
            // Add all modifiers known to have a colour, this includes mod added modifiers
            colormap = (Map<String, Color>) colormapField.get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new ReportedException(CrashReport.makeCrashReport(e, "Failed initializing InkEffects"));
        }

        if (!colormap.containsKey("Following")) {
            colormap.put("Following", new Color(0, 0, 0)); // Add Following effect to display if not present
        }

        Item unlinkedLinkbook = Integration.getMystItem(MystObjects.Items.linkbook_unlinked);

        if (unlinkedLinkbook != null) {
            Integration.allLinkbooks = colormap.keySet()
                    .stream()
                    .filter(property -> !property.equals("Relative")) // Filter out Relative to match link panels
                    .sorted()
                    .map(property -> {
                        ItemStack is = new ItemStack(unlinkedLinkbook, 1, 0);

                        NBTTagCompound compound = new NBTTagCompound();
                        NBTTagCompound linkPanelCompound = new NBTTagCompound();

                        NBTTagList list = new NBTTagList();

                        list.appendTag(new NBTTagString(property));

                        linkPanelCompound.setTag("properties", list);

                        compound.setTag("linkpanel", linkPanelCompound);

                        // Tag structure:
                        /*
                        {
                          "linkpanel": {
                            "properties": [ property ]
                          }
                        }
                         */
                        is.setTagCompound(compound);

                        return is;
                    }).collect(Collectors.toList());
        }
    }

    @Nonnull
    public static List<ItemStack> getAllLinkbooks() {
        return Integration.allLinkbooks;
    }

    private static void getMethodsAndFields() {
        try {
            Class<?> inkEffectsClass = Class.forName("com.xcompwiz.mystcraft.data.InkEffects");

            Integration.itemstack_bindings = inkEffectsClass.getDeclaredField("itemstack_bindings");
            Integration.itemstack_bindings.setAccessible(true);

            Integration.oredict_bindings = inkEffectsClass.getDeclaredField("oredict_bindings");
            Integration.oredict_bindings.setAccessible(true);

            Integration.itemId_bindings = inkEffectsClass.getDeclaredField("itemId_bindings");
            Integration.itemId_bindings.setAccessible(true);

            Class<?> mystcraftClass = Class.forName("com.xcompwiz.mystcraft.Mystcraft");

            Integration.validInks = mystcraftClass.getDeclaredField("validInks");
            Integration.validInks.setAccessible(true);

            Integration.inkcost = mystcraftClass.getDeclaredField("inkcost");
            Integration.inkcost.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new ReportedException(CrashReport.makeCrashReport(e, "Failed initializing InkEffects"));
        }
    }

    @Nonnull
    public static List<Object> getInkMixerInputs() {
        ArrayList<Object> result = new ArrayList<>();

        try {
            Map itemstack_bindings = (Map) Integration.itemstack_bindings.get(null);
            result.addAll(itemstack_bindings.keySet());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Map oredict_bindings = (Map) Integration.oredict_bindings.get(null);
            result.addAll(oredict_bindings.keySet());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Map itemId_bindings = (Map) Integration.itemId_bindings.get(null);
            result.addAll(itemId_bindings.keySet());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getValidInks() {
        try {
            return (Set<String>) Integration.validInks.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Collections.emptySet();
    }

    public static int getInkcost() {
        try {
            return Integration.inkcost.getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 50;
    }

    public static List<ItemStack> gatherAllSymbolStacks() {
        return MystSymbol.getAllRegisteredSymbols()
                .stream()
                .sorted(Comparator.comparing(IAgeSymbol::getLocalizedName))
                .map(IAgeSymbol::getRegistryName)
                .map(MystItemFactory::buildSymbolPage)
                .collect(Collectors.toList());
    }
}
