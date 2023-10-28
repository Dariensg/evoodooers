package com.helliongames.evoodooers.client.render;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.block.VoodooDollBlock;
import com.helliongames.evoodooers.entity.block.VoodooDollBlockEntity;
import com.helliongames.evoodooers.registration.EvoodooersBlocks;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class VoodooDollRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public static final ModelLayerLocation VOODOO_DOLL = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "voodoo_doll"), "main");
    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/block/voodoo_doll.png");
    private final ModelPart body;

    public VoodooDollRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(VOODOO_DOLL);
        this.body = modelPart.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(50, 52).addBox(-2.0F, -9.0F, 0.0F, 4.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(17, 32).addBox(0.0F, 0.15F, 1.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(17, 32).mirror().addBox(-8.0F, 0.15F, 1.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -8.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -1.0F, -5.25F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.0F, -2.0F, 1.0F, 0.0F, -0.3927F, 0.0F));

        body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-4.0F, -1.0F, -6.25F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.0F, 1.0F, 0.0F, 0.3927F, 0.0F));

        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 32).addBox(-5.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F))
                .texOffs(32, 0).addBox(-5.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, -8.0F, 1.0F, 0.0F, 0.0F, -0.3927F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void render(T blockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Level level = blockEntity.getLevel();
        boolean bl = level != null;
        BlockState blockState = bl ? blockEntity.getBlockState() : EvoodooersBlocks.VOODOO_DOLL.get().defaultBlockState();
        Block block = blockState.getBlock();

        if (!(block instanceof VoodooDollBlock)) return;

        poseStack.pushPose();

        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.translate(0.5, -1.5, -0.5);

        poseStack.mulPose(Axis.YP.rotationDegrees(((VoodooDollBlock) block).getYRotationDegrees(blockState)));
        VertexConsumer vertexConsumer;

        GameProfile profile = ((VoodooDollBlockEntity) blockEntity).getOwnerProfile();

        if (profile == null) {
            profile = ((VoodooDollBlockEntity) blockEntity).getUnboundProfile();
        }

        if (profile != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(profile);
            RenderType headRenderType = map.containsKey(MinecraftProfileTexture.Type.SKIN) ? RenderType.entityTranslucent(minecraft.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN)) : RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(profile)));

            vertexConsumer = multiBufferSource.getBuffer(headRenderType);
            this.render(poseStack, vertexConsumer, this.body, i, j);
        }

        vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.render(poseStack, vertexConsumer, this.body, i, j);

        poseStack.popPose();
    }

    private void render(PoseStack poseStack, VertexConsumer vertexConsumer,ModelPart body,int i, int j) {
        body.render(poseStack, vertexConsumer, i, j);
    }
}
