package me.heldplayer.mystcraft_jei.subtypes;

import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.TreeSet;

public class UnlinkedLinkbookInterpreter implements ISubtypeInterpreter {
    public static final UnlinkedLinkbookInterpreter INSTANCE = new UnlinkedLinkbookInterpreter();

    private UnlinkedLinkbookInterpreter() {
    }

    @Nullable
    @Override
    public String getSubtypeInfo(@Nonnull ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();

        if (tag != null && tag.hasKey("linkpanel", 10)) {
            Set<String> panelProperties = new TreeSet<>();

            NBTTagCompound linkPanel = tag.getCompoundTag("linkpanel");
            if (linkPanel.hasKey("properties", 9)) {
                NBTTagList properties = linkPanel.getTagList("properties", 8);
                for (int i = 0; i < properties.tagCount(); i++) {
                    panelProperties.add(properties.getStringTagAt(i));
                }
            }

            return String.join(";", panelProperties);
        }

        return "";
    }
}
