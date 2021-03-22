package mcp.mobius.waila;

import java.nio.file.Path;

import com.google.gson.GsonBuilder;
import mcp.mobius.waila.api.impl.config.WailaConfig;
import mcp.mobius.waila.network.NetworkHandler;
import mcp.mobius.waila.utils.JsonConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Waila {

    public static final String MODID = "waila";
    public static final String NAME = "Waila";
    public static final Logger LOGGER = LogManager.getLogger("Waila");

    public static JsonConfig<WailaConfig> CONFIG;

    public static Tag<Block> blockBlacklist;
    public static Tag<EntityType<?>> entityBlacklist;

    public static NetworkHandler network;
    public static WailaPlugins plugins;
    public static Path configDir;

    protected static void initConfig() {
        CONFIG = new JsonConfig<>(MODID + "/" + MODID, WailaConfig.class).withGson(new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(WailaConfig.ConfigOverlay.ConfigOverlayColor.class, new WailaConfig.ConfigOverlay.ConfigOverlayColor.Adapter())
            .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
            .create()
        );
    }

}