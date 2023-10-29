package com.helliongames.evoodooers.block;

import com.helliongames.evoodooers.entity.block.VoodooDollBlockEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
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
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level.getBlockEntity(pos) instanceof VoodooDollBlockEntity voodooDoll) {
            Player targetedPlayer = this.getTargetPlayer(null, level, voodooDoll);
            if (targetedPlayer == null) return;

            targetedPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(pos) instanceof VoodooDollBlockEntity voodooDoll) {
            ItemStack heldItem = player.getItemInHand(hand);

            if (voodooDoll.getOwnerProfile() == null && heldItem.is(Items.NAME_TAG) && heldItem.hasCustomHoverName() && (voodooDoll.getUnboundProfile() == null || !heldItem.getHoverName().getString().equals(voodooDoll.getUnboundProfile().getName()))) {
                GameProfile gameProfile = new GameProfile(null, heldItem.getHoverName().getString());
                voodooDoll.setUnboundPlayer(gameProfile);
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            }

            if (heldItem.is(Items.FLINT_AND_STEEL)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                targetedPlayer.setSecondsOnFire(1);
                heldItem.hurtAndBreak(15, player, player2 -> player2.broadcastBreakEvent(hand));
            } else if (heldItem.is(Items.FIRE_CHARGE)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                targetedPlayer.setSecondsOnFire(1);
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else if (heldItem.is(Items.POWDER_SNOW_BUCKET)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                targetedPlayer.setTicksFrozen(200);
                player.setItemInHand(hand, BucketItem.getEmptySuccessItem(heldItem, player));
            } else if (heldItem.is(Items.ECHO_SHARD)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                this.playSoundToPlayer(targetedPlayer, SoundEvents.WARDEN_EMERGE, 5.0f);

                targetedPlayer.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 160));
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else if (heldItem.is(Items.ROTTEN_FLESH)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                this.playSoundToPlayer(targetedPlayer, SoundEvents.ZOMBIE_AMBIENT, 1.0f);

                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else if (heldItem.is(Items.SPIDER_EYE)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                this.playSoundToPlayer(targetedPlayer, SoundEvents.SPIDER_AMBIENT, 1.0f);

                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else if (heldItem.is(Items.BONE)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                this.playSoundToPlayer(targetedPlayer, SoundEvents.SKELETON_AMBIENT, 1.0f);

                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else if (heldItem.is(Items.GUNPOWDER)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                this.playSoundToPlayer(targetedPlayer, SoundEvents.CREEPER_PRIMED, 1.0f);

                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else if (heldItem.is(Items.ENDER_PEARL)) {
                Player targetedPlayer = this.getTargetPlayer(player, level, voodooDoll);
                if (targetedPlayer == null) return InteractionResult.CONSUME;

                this.playSoundToPlayer(targetedPlayer, SoundEvents.ENDERMAN_STARE, 1.0f);

                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }
            } else {
                return InteractionResult.PASS;
            }

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, blockHitResult);
    }

    private void playSoundToPlayer(Player targetedPlayer, SoundEvent sound, float volume) {
        if (targetedPlayer instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(sound), SoundSource.HOSTILE, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), volume, serverPlayer.getVoicePitch(), serverPlayer.getRandom().nextLong()));
        }
    }

    public Player getTargetPlayer(@Nullable Player interactingPlayer, Level level, VoodooDollBlockEntity voodooDoll) {
        if (voodooDoll.getOwnerProfile() == null) {
            if (interactingPlayer != null) interactingPlayer.displayClientMessage(Component.translatable("block.evoodooers.voodoo_doll.no_player"), true);
            return null;
        }

        Player targetedPlayer = null;

        if (level.getServer() == null) return null;

        for (Level serverLevel : level.getServer().getAllLevels()) {
            targetedPlayer = serverLevel.getPlayerByUUID(voodooDoll.getOwnerProfile().getId());
            if (targetedPlayer != null) break;
        }

        if (targetedPlayer == null) {
            if (interactingPlayer != null) interactingPlayer.displayClientMessage(Component.translatable("block.evoodooers.voodoo_doll.no_player_found", voodooDoll.getOwnerProfile().getName()), true);
            return null;
        }

        return targetedPlayer;
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
