package mcp.mobius.waila.api;

import java.awt.Dimension;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.api.internal.ApiSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

/**
 * @see IRegistrar#addRenderer(ResourceLocation, ITooltipRenderer)
 */
@ApiSide.ClientOnly
@ApiStatus.OverrideOnly
public interface ITooltipRenderer {

    /**
     * <b>Please refrain from making a static {@link Dimension} instance since it can cause Minecraft to hang,
     * especially on Mac OS systems due to a LWJGL 3 and AWT incompatibility.
     * Instead, use {@link Suppliers#memoize(Supplier)}.</b>
     * <pre>{@code
     * static final Supplier<Dimension> DEFAULT = Suppliers.memoize(() -> new Dimension(4, 2));
     *
     * @Override
     * Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
     *     return DEFAULT.get();
     * }
     * }</pre>
     *
     * @param data     The data supplied by the provider
     * @param accessor A global accessor for BlockEntities and Entities
     *
     * @return Dimension of the reserved area
     */
    Dimension getSize(CompoundTag data, ICommonAccessor accessor);

    /**
     * Draw method for the renderer.
     *
     * @param data     The data supplied by the provider
     * @param accessor A global accessor for BlockEntities and Entities
     * @param x        The X position of this renderer
     * @param y        The Y position of this renderer
     */
    void draw(PoseStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y);

}
