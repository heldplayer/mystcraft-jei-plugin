package me.heldplayer.mystcraft_jei;

import net.minecraft.util.text.translation.I18n;

public class Util {

    @SuppressWarnings("deprecation")
    public static String translate(String key) {
        if (I18n.canTranslate(key)) {
            return I18n.translateToLocal(key);
        }
        return I18n.translateToFallback(key);
    }
}
