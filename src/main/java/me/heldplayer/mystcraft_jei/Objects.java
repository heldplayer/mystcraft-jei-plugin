package me.heldplayer.mystcraft_jei;

import org.apache.logging.log4j.Logger;

/**
 * MystNEIPlugin mod Objects
 *
 * @author heldplayer
 */
public final class Objects {
    public static final String MOD_ID = "mystcraft-jei-plugin";
    public static final String MOD_NAME = "Mystcraft JEI Plugin";
    // Dependencies: https://github.com/MinecraftForge/MinecraftForge/blob/master/fml/src/main/java/net/minecraftforge/fml/common/versioning/VersionRange.java#L100
    public static final String MOD_DEPENDENCIES = "after:*;"
            + "required-after:jei;"
            + "required-after:mystcraft@[0.13.0,);";

    public static final String CLIENT_PROXY = "me.heldplayer.mystcraft_jei.client.ClientProxy";
    public static final String SERVER_PROXY = "me.heldplayer.mystcraft_jei.CommonProxy";
    public static Logger log;
}
