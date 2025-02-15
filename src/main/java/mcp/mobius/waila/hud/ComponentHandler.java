package mcp.mobius.waila.hud;

import java.util.List;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.config.PluginConfig;
import mcp.mobius.waila.data.DataAccessor;
import mcp.mobius.waila.registry.Registrar;
import mcp.mobius.waila.util.ExceptionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

// TODO: remove deprecated method calls
@SuppressWarnings("deprecation")
public class ComponentHandler {

    public static void gatherBlock(DataAccessor accessor, Tooltip tooltip, TooltipPosition position) {
        Registrar registrar = Registrar.INSTANCE;
        Block block = accessor.getBlock();
        BlockEntity blockEntity = accessor.getBlockEntity();

        int rate = Waila.config.get().getGeneral().getRateLimit();

        if (blockEntity != null && accessor.isTimeElapsed(rate) && Waila.config.get().getGeneral().isDisplayTooltip()) {
            accessor.resetTimer();
            if (!(registrar.blockData.get(block).isEmpty() && registrar.blockData.get(blockEntity).isEmpty())) {
                Waila.packet.requestBlock(blockEntity);
            }
        }

        handleBlock(accessor, tooltip, block, position);
        handleBlock(accessor, tooltip, blockEntity, position);
    }

    private static void handleBlock(DataAccessor accessor, Tooltip tooltip, Object obj, TooltipPosition position) {
        Registrar registrar = Registrar.INSTANCE;
        List<IBlockComponentProvider> providers = registrar.blockComponent.get(position).get(obj);
        for (IBlockComponentProvider provider : providers) {
            try {
                switch (position) {
                    case HEAD -> {
                        provider.appendHead((ITooltip) tooltip, accessor, PluginConfig.INSTANCE);
                        provider.appendHead((List<Component>) tooltip, accessor, PluginConfig.INSTANCE);
                    }
                    case BODY -> {
                        provider.appendBody((ITooltip) tooltip, accessor, PluginConfig.INSTANCE);
                        provider.appendBody((List<Component>) tooltip, accessor, PluginConfig.INSTANCE);
                    }
                    case TAIL -> {
                        provider.appendTail((ITooltip) tooltip, accessor, PluginConfig.INSTANCE);
                        provider.appendTail((List<Component>) tooltip, accessor, PluginConfig.INSTANCE);
                    }
                }
            } catch (Throwable e) {
                ExceptionUtil.dump(e, provider.getClass().toString(), tooltip);
            }
        }
    }

    public static void gatherEntity(Entity entity, DataAccessor accessor, Tooltip tooltip, TooltipPosition position) {
        Registrar registrar = Registrar.INSTANCE;
        Entity trueEntity = accessor.getEntity();

        int rate = Waila.config.get().getGeneral().getRateLimit();

        if (trueEntity != null && accessor.isTimeElapsed(rate)) {
            accessor.resetTimer();

            if (!registrar.entityData.get(trueEntity).isEmpty()) {
                Waila.packet.requestEntity(trueEntity);
            }
        }

        List<IEntityComponentProvider> providers = registrar.entityComponent.get(position).get(entity);
        for (IEntityComponentProvider provider : providers) {
            try {
                switch (position) {
                    case HEAD -> {
                        provider.appendHead((ITooltip) tooltip, accessor, PluginConfig.INSTANCE);
                        provider.appendHead((List<Component>) tooltip, accessor, PluginConfig.INSTANCE);
                    }
                    case BODY -> {
                        provider.appendBody((ITooltip) tooltip, accessor, PluginConfig.INSTANCE);
                        provider.appendBody((List<Component>) tooltip, accessor, PluginConfig.INSTANCE);
                    }
                    case TAIL -> {
                        provider.appendTail((ITooltip) tooltip, accessor, PluginConfig.INSTANCE);
                        provider.appendTail((List<Component>) tooltip, accessor, PluginConfig.INSTANCE);
                    }
                }
            } catch (Throwable e) {
                ExceptionUtil.dump(e, provider.getClass().toString(), tooltip);
            }
        }
    }

    public static ItemStack getDisplayItem(HitResult target) {
        Registrar registrar = Registrar.INSTANCE;
        DataAccessor data = DataAccessor.INSTANCE;
        PluginConfig config = PluginConfig.INSTANCE;

        if (target.getType() == HitResult.Type.ENTITY) {
            List<IEntityComponentProvider> providers = registrar.entityItem.get(data.getEntity());
            for (IEntityComponentProvider provider : providers) {
                ItemStack providerStack = provider.getDisplayItem(data, config);
                if (!providerStack.isEmpty()) {
                    return providerStack;
                }
            }
        } else {
            BlockState state = data.getBlockState();
            if (state.isAir())
                return ItemStack.EMPTY;

            List<IBlockComponentProvider> providers = registrar.blockItem.get(state.getBlock());
            for (IBlockComponentProvider provider : providers) {
                ItemStack providerStack = provider.getDisplayItem(data, config);
                if (!providerStack.isEmpty()) {
                    return providerStack;
                }
            }

            BlockEntity blockEntity = data.getBlockEntity();
            if (blockEntity != null) {
                providers = registrar.blockItem.get(blockEntity);
                for (IBlockComponentProvider provider : providers) {
                    ItemStack providerStack = provider.getDisplayItem(data, config);
                    if (!providerStack.isEmpty()) {
                        return providerStack;
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public static Entity getOverrideEntity(HitResult target) {
        if (target == null || target.getType() != HitResult.Type.ENTITY) {
            return null;
        }

        Registrar registrar = Registrar.INSTANCE;
        Entity entity = ((EntityHitResult) target).getEntity();

        List<IEntityComponentProvider> overrideProviders = registrar.entityOverride.get(entity);
        for (IEntityComponentProvider provider : overrideProviders) {
            Entity override = provider.getOverride(DataAccessor.INSTANCE, PluginConfig.INSTANCE);
            if (override != null) {
                return override;
            }
        }

        return entity;
    }

    public static BlockState getOverrideBlock(HitResult target) {
        Registrar registrar = Registrar.INSTANCE;

        Level world = Minecraft.getInstance().level;
        BlockPos pos = ((BlockHitResult) target).getBlockPos();
        //noinspection ConstantConditions
        BlockState state = world.getBlockState(pos);

        List<IBlockComponentProvider> providers = registrar.blockOverride.get(state.getBlock());
        for (IBlockComponentProvider provider : providers) {
            BlockState override = provider.getOverride(DataAccessor.INSTANCE, PluginConfig.INSTANCE);
            if (override != null) {
                return override;
            }
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        providers = registrar.blockOverride.get(blockEntity);
        for (IBlockComponentProvider provider : providers) {
            BlockState override = provider.getOverride(DataAccessor.INSTANCE, PluginConfig.INSTANCE);
            if (override != null) {
                return override;
            }
        }

        return state;
    }

}
