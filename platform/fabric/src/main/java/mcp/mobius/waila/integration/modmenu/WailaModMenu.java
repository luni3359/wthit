package mcp.mobius.waila.integration.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import mcp.mobius.waila.gui.screen.HomeConfigScreen;

public class WailaModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return HomeConfigScreen::new;
    }

}
