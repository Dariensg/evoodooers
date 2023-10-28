package com.helliongames.evoodooers.entity.block;

import com.google.common.collect.Iterables;
import com.helliongames.evoodooers.registration.EvoodooersBlockEntities;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.Services;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class VoodooDollBlockEntity extends BlockEntity {
    public static final String TAG_CONNECTED_PLAYER = "ConnectedPlayer";
    public static final String TAG_UNBOUND_PLAYER = "UnboundPlayer";
    @Nullable
    private static GameProfileCache profileCache;
    @Nullable
    private static MinecraftSessionService sessionService;
    @Nullable
    private static Executor mainThreadExecutor;
    @Nullable
    private GameProfile owner;
    @Nullable
    private GameProfile unboundPlayer;
    public VoodooDollBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public VoodooDollBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(EvoodooersBlockEntities.VOODOO_DOLL.get(), blockPos, blockState);
    }

    public static void setup(Services services, Executor executor) {
        profileCache = services.profileCache();
        sessionService = services.sessionService();
        mainThreadExecutor = executor;
    }

    public static void clear() {
        profileCache = null;
        sessionService = null;
        mainThreadExecutor = null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.owner != null) {
            CompoundTag compoundTag2 = new CompoundTag();
            NbtUtils.writeGameProfile(compoundTag2, this.owner);
            tag.put(TAG_CONNECTED_PLAYER, compoundTag2);
        }
        if (this.unboundPlayer != null) {
            CompoundTag compoundTag2 = new CompoundTag();
            NbtUtils.writeGameProfile(compoundTag2, this.unboundPlayer);
            tag.put(TAG_UNBOUND_PLAYER, compoundTag2);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_CONNECTED_PLAYER, 10)) {
            this.setOwner(NbtUtils.readGameProfile(tag.getCompound(TAG_CONNECTED_PLAYER)));
        }
        if (tag.contains(TAG_UNBOUND_PLAYER, 10)) {
            this.setUnboundPlayer(NbtUtils.readGameProfile(tag.getCompound(TAG_UNBOUND_PLAYER)));
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Nullable
    public GameProfile getOwnerProfile() {
        return this.owner;
    }

    @Nullable
    public GameProfile getUnboundProfile() {
        return this.unboundPlayer;
    }

    public void setOwner(@Nullable GameProfile profile) {
        synchronized(this) {
            this.owner = profile;
        }

        this.updateOwnerProfile();

        if (this.owner != null) {
            this.unboundPlayer = null;
        }
    }

    public void setUnboundPlayer(@Nullable GameProfile profile) {
        synchronized(this) {
            this.unboundPlayer = profile;
        }

        this.updateUnboundProfile();
    }

    private void updateOwnerProfile() {
        updateGameprofile(this.owner, (profile) -> {
            this.owner = profile;
            this.setChanged();
        });
    }

    private void updateUnboundProfile() {
        updateGameprofile(this.unboundPlayer, (profile) -> {
            this.unboundPlayer = profile;
            this.setChanged();
        });
    }

    public static void updateGameprofile(@Nullable GameProfile gameProfile, Consumer<GameProfile> profileConsumer) {
        if (gameProfile != null && !StringUtil.isNullOrEmpty(gameProfile.getName()) && (!gameProfile.isComplete() || !gameProfile.getProperties().containsKey("textures")) && profileCache != null && sessionService != null) {
            profileCache.getAsync(gameProfile.getName(), (p_182470_) -> {
                Util.backgroundExecutor().execute(() -> {
                    Util.ifElse(p_182470_, (p_276255_) -> {
                        Property property = Iterables.getFirst(p_276255_.getProperties().get("textures"), (Property)null);
                        if (property == null) {
                            MinecraftSessionService minecraftsessionservice = sessionService;
                            if (minecraftsessionservice == null) {
                                return;
                            }

                            p_276255_ = minecraftsessionservice.fillProfileProperties(p_276255_, true);
                        }

                        GameProfile gameprofile = p_276255_;
                        Executor executor = mainThreadExecutor;
                        if (executor != null) {
                            executor.execute(() -> {
                                GameProfileCache gameprofilecache = profileCache;
                                if (gameprofilecache != null) {
                                    gameprofilecache.add(gameprofile);
                                    profileConsumer.accept(gameprofile);
                                }

                            });
                        }

                    }, () -> {
                        Executor executor = mainThreadExecutor;
                        if (executor != null) {
                            executor.execute(() -> {
                                profileConsumer.accept(gameProfile);
                            });
                        }

                    });
                });
            });
        } else {
            profileConsumer.accept(gameProfile);
        }
    }
}
