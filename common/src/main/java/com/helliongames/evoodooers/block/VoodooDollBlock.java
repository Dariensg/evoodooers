package com.helliongames.evoodooers.block;

import com.helliongames.evoodooers.entity.block.VoodooDollBlockEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class VoodooDollBlock extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public VoodooDollBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(pos) instanceof VoodooDollBlockEntity voodooDoll) {
            if (voodooDoll.getOwnerProfile() == null) {
                player.displayClientMessage(Component.translatable("block.evoodooers.voodoo_doll.no_player"), true);
                return InteractionResult.CONSUME;
            }

            Player targetedPlayer = level.getPlayerByUUID(voodooDoll.getOwnerProfile().getId());
            if (targetedPlayer == null) {
                player.displayClientMessage(Component.translatable("block.evoodooers.voodoo_doll.no_player_found", voodooDoll.getOwnerProfile().getName()), true);
                return InteractionResult.CONSUME;
            };

            if (player.getItemInHand(hand).is(Items.FLINT_AND_STEEL) ) {
                targetedPlayer.setSecondsOnFire(1);
                player.getItemInHand(hand).hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(hand));
            } else if (player.getItemInHand(hand).is(Items.POWDER_SNOW_BUCKET)) {
                targetedPlayer.setTicksFrozen(200);
                player.setItemInHand(hand, BucketItem.getEmptySuccessItem(player.getItemInHand(hand), player));
            } else {
                return InteractionResult.PASS;
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, blockHitResult);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos, BlockState state) {
        ItemStack stack = new ItemStack(this);
        if (getter.getBlockEntity(pos) instanceof VoodooDollBlockEntity voodooDollBlockEntity && voodooDollBlockEntity.getOwnerProfile() != null) {
            stack.getOrCreateTag().putString("ConnectedPlayer", voodooDollBlockEntity.getOwnerProfile().getName());
        }

        return stack;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof VoodooDollBlockEntity) {
            VoodooDollBlockEntity voodooDollBlockEntity = (VoodooDollBlockEntity)blockEntity;
            GameProfile gameProfile = null;
            if (stack.hasTag()) {
                CompoundTag compoundTag = stack.getTag();
                if (compoundTag.contains("ConnectedPlayer", 10)) {
                    gameProfile = NbtUtils.readGameProfile(compoundTag.getCompound("ConnectedPlayer"));
                } else if (compoundTag.contains("ConnectedPlayer", 8) && !Util.isBlank(compoundTag.getString("ConnectedPlayer"))) {
                    gameProfile = new GameProfile(null, compoundTag.getString("ConnectedPlayer"));
                }
            }
            voodooDollBlockEntity.setOwner(gameProfile);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(blockPlaceContext.getRotation() + 180.0f));
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    public float getYRotationDegrees(BlockState blockState) {
        return RotationSegment.convertToDegrees(blockState.getValue(ROTATION));
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(ROTATION, rotation.rotate(blockState.getValue(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.setValue(ROTATION, mirror.mirror(blockState.getValue(ROTATION), 16));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new VoodooDollBlockEntity(blockPos, blockState);
    }
}
