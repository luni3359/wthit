package mcp.mobius.waila.plugin.test;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.IWailaConfig;
import mcp.mobius.waila.api.WailaConstants;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public enum OverrideTest implements IBlockComponentProvider {

    INSTANCE;

    static final ResourceLocation MOD_NAME = new ResourceLocation("test:override_mod_name");

    @Override
    public void appendTail(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(WailaConstants.CONFIG_SHOW_MOD_NAME) && config.getBoolean(MOD_NAME)) {
            tooltip.set(WailaConstants.MOD_NAME_TAG, new TextComponent(IWailaConfig.get().getFormatting().formatModName("Overrode")));
        }
    }

}
