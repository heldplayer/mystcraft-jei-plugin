package me.heldplayer.mystcraft_jei.integration.mystcraft;

import com.xcompwiz.mystcraft.api.hook.SymbolAPI;
import com.xcompwiz.mystcraft.api.symbol.IAgeSymbol;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MystSymbol {
    private static SymbolAPI api;

    protected static void setAPI(@Nonnull Object api) {
        MystSymbol.api = (SymbolAPI) api;
    }

    public static boolean isReady() {
        return api != null;
    }

    @Nonnull
    public static List<IAgeSymbol> getAllRegisteredSymbols() {
        return api.getAllRegisteredSymbols();
    }

    @Nullable
    public static IAgeSymbol getSymbol(@Nonnull ResourceLocation s) {
        return api.getSymbol(s);
    }
}
