package me.heldplayer.mystcraft_jei.subtypes;

import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.TreeSet;

public class PageSubtypeInterpreter implements ISubtypeInterpreter {
    public static final PageSubtypeInterpreter INSTANCE = new PageSubtypeInterpreter();

    private PageSubtypeInterpreter() {
    }

    @Nullable
    @Override
    public String getSubtypeInfo(@Nonnull ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            return null;
        }
        boolean hasSymbol = tag.hasKey("symbol", 8);
        boolean hasLinkPanel = tag.hasKey("linkpanel", 10);
        if (hasSymbol && hasLinkPanel || !hasSymbol && !hasLinkPanel) {
            return null; // Don't add info for pages that have more than one thing
        }

        if (hasSymbol) {
            return "Symbol:" + tag.getString("symbol");
        } else {
            Set<String> panelProperties = new TreeSet<>();

            NBTTagCompound linkPanel = tag.getCompoundTag("linkpanel");
            if (linkPanel.hasKey("properties", 9)) {
                NBTTagList properties = linkPanel.getTagList("properties", 8);
                for (int i = 0; i < properties.tagCount(); i++) {
                    panelProperties.add(properties.getStringTagAt(i));
                }
            }

            return "LinkPanel:" + String.join(";", panelProperties);
        }
    }
}
