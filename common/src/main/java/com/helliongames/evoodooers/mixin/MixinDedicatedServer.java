package com.helliongames.evoodooers.mixin;

import com.helliongames.evoodooers.entity.block.VoodooDollBlockEntity;
import com.mojang.datafixers.DataFixer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;

@Mixin(DedicatedServer.class)
public abstract class MixinDedicatedServer extends MinecraftServer {
    public MixinDedicatedServer(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, Proxy $$4, DataFixer $$5, Services $$6, ChunkProgressListenerFactory $$7) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }

    @Inject(method = "initServer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SkullBlockEntity;setup(Lnet/minecraft/server/Services;Ljava/util/concurrent/Executor;)V"))
    private void evoodooers_setupVoodooDollServer(CallbackInfoReturnable<Boolean> cir) {
        VoodooDollBlockEntity.setup(this.services, this);
    }

    @Inject(method = "stopServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SkullBlockEntity;clear()V"))
    private void evoodooers_clearVoodooDollOnServerStop(CallbackInfo ci) {
        VoodooDollBlockEntity.clear();
    }
}
