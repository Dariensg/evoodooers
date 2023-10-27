package com.helliongames.evoodooers.platform;

import com.helliongames.evoodooers.client.render.VoodooDollRenderer;
import com.helliongames.evoodooers.platform.services.IClientHelper;
import com.helliongames.evoodooers.registration.EvoodooersBlockEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "evoodooers", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientHelper implements IClientHelper {
    private static final Map<ModelLayerLocation, Supplier<LayerDefinition>> modelLayers = new HashMap<>();
    private static final Map<BlockEntityType, BlockEntityRendererProvider> blockEntityRenderers = new HashMap<>();

    @Override
    public void registerEntityRenderers() {
    }

    @Override
    public void registerModelLayers() {
    }

    @Override
    public void registerRenderTypes() {
    }

    @SubscribeEvent
    public static void registerModelLayerListener(EntityRenderersEvent.RegisterLayerDefinitions event) {
        modelLayers.put(VoodooDollRenderer.VOODOO_DOLL, VoodooDollRenderer::createBodyLayer);

        for (Map.Entry<ModelLayerLocation, Supplier<LayerDefinition>> entry : modelLayers.entrySet()) {
            event.registerLayerDefinition(entry.getKey(), entry.getValue());
        }
    }

    @SubscribeEvent
    public static void registerEntityRendererListener(EntityRenderersEvent.RegisterRenderers event) {
        blockEntityRenderers.put(EvoodooersBlockEntities.VOODOO_DOLL.get(), VoodooDollRenderer::new);

        for (Map.Entry<BlockEntityType, BlockEntityRendererProvider> entry : blockEntityRenderers.entrySet()) {
            event.registerBlockEntityRenderer(entry.getKey(), entry.getValue());
        }
    }
}
