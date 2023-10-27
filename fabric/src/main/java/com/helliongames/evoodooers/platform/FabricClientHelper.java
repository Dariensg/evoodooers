package com.helliongames.evoodooers.platform;

import com.helliongames.evoodooers.client.render.VoodooDollRenderer;
import com.helliongames.evoodooers.platform.services.IClientHelper;
import com.helliongames.evoodooers.registration.EvoodooersBlockEntities;
import com.helliongames.evoodooers.registration.EvoodooersBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FabricClientHelper implements IClientHelper {
    @Override
    public void registerEntityRenderers() {
        BlockEntityRenderers.register(EvoodooersBlockEntities.VOODOO_DOLL.get(), VoodooDollRenderer::new);
    }

    @Override
    public void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(VoodooDollRenderer.VOODOO_DOLL, VoodooDollRenderer::createBodyLayer);
    }

    @Override
    public void registerRenderTypes() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), EvoodooersBlocks.VOODOO_DOLL.get());
    }
}
