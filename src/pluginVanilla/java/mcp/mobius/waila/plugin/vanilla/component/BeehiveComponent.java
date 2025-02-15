package mcp.mobius.waila.plugin.vanilla.component;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.plugin.vanilla.config.Options;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;

public enum BeehiveComponent implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(Options.LEVEL_HONEY)) {
            BlockState state = accessor.getBlockState();
            tooltip.addPair(
                new TranslatableComponent("tooltip.waila.honey_level"),
                new TextComponent(state.getValue(BeehiveBlock.HONEY_LEVEL).toString()));
        }
    }

}
