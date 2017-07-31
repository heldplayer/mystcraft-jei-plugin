package me.heldplayer.mystcraft_jei.integration.mystcraft;

import com.xcompwiz.mystcraft.api.APIInstanceProvider;
import com.xcompwiz.mystcraft.api.exception.APIUndefined;
import com.xcompwiz.mystcraft.api.exception.APIVersionRemoved;
import com.xcompwiz.mystcraft.api.exception.APIVersionUndefined;
import me.heldplayer.mystcraft_jei.Objects;
import me.heldplayer.mystcraft_jei.util.Entry;

@Entry
public class MystcraftIntegration {
    @Entry
    public static void register(APIInstanceProvider provider) {
        Objects.log.info("Received the Mystcraft API provider");

        MystcraftIntegration.getLinkPropertyAPI(provider);
        MystcraftIntegration.getSymbolAPI(provider);
        MystcraftIntegration.getItemFactoryAPI(provider);
        MystcraftIntegration.getRenderAPI(provider);
        MystcraftIntegration.getPageAPI(provider);
    }

    private static void getLinkPropertyAPI(APIInstanceProvider provider) {
        try {
            Object api = provider.getAPIInstance("linkingprop-1");
            MystLinkProperty.setAPI(api);
        } catch (APIUndefined e) {
            Objects.log.error("The Mystcraft linkingprop API is missing", e);
        } catch (APIVersionUndefined e) {
            Objects.log.error("Mystcraft can't count to 1, missing the linkingprop API", e);
        } catch (APIVersionRemoved e) {
            Objects.log.error("Version 1 of the Mystcraft linkingprop API was removed, NEI Mystcraft Plugin needs to be updated", e);
        }
    }

    private static void getSymbolAPI(APIInstanceProvider provider) {
        try {
            Object api = provider.getAPIInstance("symbol-1");
            MystSymbol.setAPI(api);
        } catch (APIUndefined e) {
            Objects.log.error("The Mystcraft symbol API is missing", e);
        } catch (APIVersionUndefined e) {
            Objects.log.error("Mystcraft can't count to 1, missing the symbol API", e);
        } catch (APIVersionRemoved e) {
            Objects.log.error("Version 1 of the Mystcraft symbol API was removed, NEI Mystcraft Plugin needs to be updated", e);
        }
    }

    private static void getItemFactoryAPI(APIInstanceProvider provider) {
        try {
            Object api = provider.getAPIInstance("itemfact-1");
            MystItemFactory.setAPI(api);
        } catch (APIUndefined e) {
            Objects.log.error("The Mystcraft itemfact API is missing", e);
        } catch (APIVersionUndefined e) {
            Objects.log.error("Mystcraft can't count to 1, missing the itemfact API", e);
        } catch (APIVersionRemoved e) {
            Objects.log.error("Version 1 of the Mystcraft itemfact API was removed, NEI Mystcraft Plugin needs to be updated", e);
        }
    }

    private static void getRenderAPI(APIInstanceProvider provider) {
        try {
            Object api = provider.getAPIInstance("render-1");
            MystRender.setAPI(api);
        } catch (APIUndefined e) {
            Objects.log.error("The Mystcraft render API is missing", e);
        } catch (APIVersionUndefined e) {
            Objects.log.error("Mystcraft can't count to 1, missing the render API", e);
        } catch (APIVersionRemoved e) {
            Objects.log.error("Version 1 of the Mystcraft render API was removed, NEI Mystcraft Plugin needs to be updated", e);
        }
    }

    private static void getPageAPI(APIInstanceProvider provider) {
        try {
            Object api = provider.getAPIInstance("page-1");
            MystPage.setAPI(api);
        } catch (APIUndefined e) {
            Objects.log.error("The Mystcraft page API is missing", e);
        } catch (APIVersionUndefined e) {
            Objects.log.error("Mystcraft can't count to 1, missing the page API", e);
        } catch (APIVersionRemoved e) {
            Objects.log.error("Version 1 of the Mystcraft page API was removed, NEI Mystcraft Plugin needs to be updated", e);
        }
    }
}
