package me.heldplayer.mystcraft_jei;

import me.heldplayer.mystcraft_jei.util.Integration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Objects.MOD_ID, name = Objects.MOD_NAME, dependencies = Objects.MOD_DEPENDENCIES)
public class MystcraftJEI {

    @Mod.Instance(value = Objects.MOD_ID)
    public static MystcraftJEI instance;

    @SidedProxy(clientSide = Objects.CLIENT_PROXY, serverSide = Objects.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Objects.log = event.getModLog();

        Integration.initialize();
        proxy.registerEventHandlers();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("mystcraft", "API", "me.heldplayer.mystcraft_jei.integration.mystcraft.MystcraftIntegration.register");
    }
}
