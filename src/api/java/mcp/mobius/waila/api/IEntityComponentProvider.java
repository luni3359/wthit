package mcp.mobius.waila.api;

import java.util.List;

import mcp.mobius.waila.api.internal.ApiSide;
import mcp.mobius.waila.impl.Impl;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * Interface used to provide Block/BlockEntity tooltip information to Waila.
 * <p>
 * All methods in this interface shouldn't to be called by the implementing mod. An instance of the class is to be
 * registered to Waila via the {@link IRegistrar} instance provided in the original registration method.
 *
 * @see IWailaPlugin
 * @see IRegistrar
 */
@ApiSide.ClientOnly
@ApiStatus.OverrideOnly
public interface IEntityComponentProvider {

    /**
     * An "empty" entity to be used with {@link #getOverride}, effectively disabling the tooltip to be displayed.
     * <p>
     * <b>Note:</b> Waila will use {@code ==} instead of {@link Object#equals} to check for this.
     */
    Entity EMPTY_ENTITY = Impl.allocate(AreaEffectCloud.class);

    /**
     * Callback used to override the default Waila lookup system.
     *
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     *
     * @return null if override is not required, an Entity otherwise.
     *
     * @see IRegistrar#addOverride(IEntityComponentProvider, Class, int)
     * @see #EMPTY_ENTITY
     */
    @Nullable
    default Entity getOverride(IEntityAccessor accessor, IPluginConfig config) {
        return null;
    }

    /**
     * Callback used to set an item to display in the tooltip.
     * <p>
     * This method is only called on the client side.
     * If you require data from the server, you should also implement {@link IServerDataProvider#appendServerData}
     * and add the data to the {@link CompoundTag} there, which can then be read back using {@link IEntityAccessor#getServerData}.
     * If you rely on the client knowing the data you need, you are not guaranteed to have the proper values.
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
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).
     * <p>
     * This method is only called on the client side.
     * If you require data from the server, you should also implement {@link IServerDataProvider#appendServerData}
     * and add the data to the {@link CompoundTag} there, which can then be read back using {@link IEntityAccessor#getServerData}.
     * If you rely on the client knowing the data you need, you are not guaranteed to have the proper values.
     *
     * @param tooltip  Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
     *                 Use {@link ITooltip#set(ResourceLocation, Component)} with tags from {@link WailaConstants} to override built-in values.
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     *
     * @see IRegistrar#addComponent(IEntityComponentProvider, TooltipPosition, Class, int)
     */
    default void appendHead(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).
     * <p>
     * This method is only called on the client side.
     * If you require data from the server, you should also implement {@link IServerDataProvider#appendServerData}
     * and add the data to the {@link CompoundTag} there, which can then be read back using {@link IEntityAccessor#getServerData}.
     * If you rely on the client knowing the data you need, you are not guaranteed to have the proper values.
     *
     * @param tooltip  Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
     *                 Use {@link ITooltip#set(ResourceLocation, Component)} with tags from {@link WailaConstants} to override built-in values.
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     *
     * @see IRegistrar#addComponent(IEntityComponentProvider, TooltipPosition, Class, int)
     */
    default void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).
     * <p>
     * This method is only called on the client side.
     * If you require data from the server, you should also implement {@link IServerDataProvider#appendServerData}
     * and add the data to the {@link CompoundTag} there, which can then be read back using {@link IEntityAccessor#getServerData}.
     * If you rely on the client knowing the data you need, you are not guaranteed to have the proper values.
     *
     * @param tooltip  Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
     *                 Use {@link ITooltip#set(ResourceLocation, Component)} with tags from {@link WailaConstants} to override built-in values.
     * @param accessor Contains most of the relevant information about the current environment.
     * @param config   Current configuration of Waila.
     *
     * @see IRegistrar#addComponent(IEntityComponentProvider, TooltipPosition, Class, int)
     */
    default void appendTail(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * @deprecated use {@link #appendHead(ITooltip, IEntityAccessor, IPluginConfig)}
     */
    @Deprecated
    default void appendHead(List<Component> tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * @deprecated use {@link #appendBody(ITooltip, IEntityAccessor, IPluginConfig)}
     */
    @Deprecated
    default void appendBody(List<Component> tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

    /**
     * @deprecated use {@link #appendTail(ITooltip, IEntityAccessor, IPluginConfig)}
     */
    @Deprecated
    default void appendTail(List<Component> tooltip, IEntityAccessor accessor, IPluginConfig config) {
    }

}
