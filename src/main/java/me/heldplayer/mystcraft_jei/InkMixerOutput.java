package me.heldplayer.mystcraft_jei;

import com.xcompwiz.mystcraft.api.util.ColorGradient;
import me.heldplayer.mystcraft_jei.integration.mystcraft.MystLinkProperty;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class InkMixerOutput {
    private final ColorGradient gradient;
    private final String[] modifiers;

    public InkMixerOutput(@Nullable ColorGradient gradient, @Nonnull String[] modifiers) {
        this.gradient = modifiers.length > 0 ? gradient : null;
        this.modifiers = modifiers;
    }

    @Nullable
    public ColorGradient getGradient() {
        return gradient;
    }

    @Nonnull
    public String[] getModifiers() {
        return modifiers;
    }

    @Nullable
    public static InkMixerOutput getOutputFor(@Nonnull ItemStack input) {
        if (!MystLinkProperty.isReady()) {
            return null;
        }

        Map<String, Float> properties = MystLinkProperty.getPropertiesForItem(input);

        if (properties == null) {
            return null;
        }

        String[] modifiers = properties.keySet().stream()
                .filter(java.util.Objects::nonNull)
                .filter((mod) -> !mod.isEmpty())
                .toArray(String[]::new);

        ColorGradient gradient = modifiers.length > 0 ? MystLinkProperty.getPropertiesGradient(properties) : null;

        return new InkMixerOutput(gradient, modifiers);
    }
}
