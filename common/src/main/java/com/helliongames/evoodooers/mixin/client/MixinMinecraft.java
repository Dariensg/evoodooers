package com.helliongames.evoodooers.mixin.client;

import com.helliongames.evoodooers.entity.block.VoodooDollBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.Instant;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "doWorldLoad",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SkullBlockEntity;setup(Lnet/minecraft/server/Services;Ljava/util/concurrent/Executor;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void evoodooers_setupVoodooDollOnWorldLoad(String $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, boolean $$4, CallbackInfo ci, Instant $$5, Services services) {
        VoodooDollBlockEntity.setup(services, (Minecraft) (Object) this);
    }

    @Inject(method = "setLevel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SkullBlockEntity;setup(Lnet/minecraft/server/Services;Ljava/util/concurrent/Executor;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void evoodooers_setupVoodooDollOnSetLevel(ClientLevel $$0, CallbackInfo ci, ProgressScreen $$1, Services services) {
        VoodooDollBlockEntity.setup(services, (Minecraft) (Object) this);
    }

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SkullBlockEntity;clear()V"))
    private void evoodooers_clearVoodooDollOnClear(Screen $$0, CallbackInfo ci) {
        VoodooDollBlockEntity.clear();
    }
}
