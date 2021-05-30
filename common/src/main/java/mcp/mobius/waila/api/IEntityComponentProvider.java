package mcp.mobius.waila.api;

import java.util.List;

import mcp.mobius.waila.api.internal.ApiSide;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

/**
 * Callback class interface used to provide Entity tooltip information to Waila.<br>
 * All methods in this interface shouldn't to be called by the implementing mod. An instance of the class is to be
 * registered to Waila via the {@link IRegistrar} instance provided in the original registration callback method
 * (cf. {@link IRegistrar} documentation for more information).
 */
@ApiSide.ClientOnly
@ApiStatus.OverrideOnly
public interface IEntityComponentProvider {

    /**
     * Callback used to override the default Waila lookup system.</br>
     * Will only be called if the implementing class is registered via {@link IRegistrar#addOverride}.</br>
     *
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     *
     * @return null if override is not required, an Entity otherwise.
     */
    default Entity getOverride(IEntityAccessor accessor, IPluginConfig config) {
        return null;
    }

    /**
     * Callback used to set an item to display alongside the entity name in the tooltip, similar to how blocks are treated.
     * Will only be called if the implementing class is registered via {@link}
     *
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     *
     * @return The item to display or {@link ItemStack#EMPTY} to display nothing.
     */
    default ItemStack getDisplayItem(IEntityAccessor accessor, IPluginConfig config) {
        return ItemStack.EMPTY;
    }

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
     * Will only be called if the implementing class is registered via {@link IRegistrar#addComponent(IEntityComponentProvider,
     * TooltipPosition, Class)}.</br>
     * You are supposed to always return the modified input current tip.</br>
     * <p>
     * This method is only called on the client side. If you require data from the server, you should also implement
     * {@link IServerDataProvider#appendServerData(CompoundTag, ServerPlayerEntity, World, Object)} and add the data to the {@link CompoundTag}
     * there, which can then be read back using {@link IEntityAccessor#getServerData()}. If you rely on the client knowing
     * the data you need, you are not guaranteed to have the proper values.
     *
     * @param tooltip  Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     */
    default void appendHead(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
     * Will only be called if the implementing class is registered via {@link IRegistrar#addComponent(IEntityComponentProvider,
     * TooltipPosition, Class)}.</br>
     * You are supposed to always return the modified input current tip.</br>
     * <p>
     * This method is only called on the client side. If you require data from the server, you should also implement
     * {@link IServerDataProvider#appendServerData(CompoundTag, ServerPlayerEntity, World, Object)} and add the data to the {@link CompoundTag}
     * there, which can then be read back using {@link IEntityAccessor#getServerData()}. If you rely on the client knowing
     * the data you need, you are not guaranteed to have the proper values.
     *
     * @param tooltip  Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     */
    default void appendBody(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
     * Will only be called if the implementing class is registered via {@link IRegistrar#addComponent(IEntityComponentProvider,
     * TooltipPosition, Class)}.</br>
     * You are supposed to always return the modified input current tip.</br>
     * <p>
     * This method is only called on the client side. If you require data from the server, you should also implement
     * {@link IServerDataProvider#appendServerData(CompoundTag, ServerPlayerEntity, World, Object)} and add the data to the {@link CompoundTag}
     * there, which can then be read back using {@link IEntityAccessor#getServerData()}. If you rely on the client knowing
     * the data you need, you are not guaranteed to have the proper values.
     *
     * @param tooltip  Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     */
    default void appendTail(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

}
