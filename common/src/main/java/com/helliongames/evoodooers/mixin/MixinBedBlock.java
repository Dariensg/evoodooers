package com.helliongames.evoodooers.mixin;

import com.helliongames.evoodooers.access.BedBlockEntityAccess;
import com.helliongames.evoodooers.registration.EvoodooersItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public abstract class MixinBedBlock {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void evoodooers_getHairFromBed(BlockState $$0, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult $$5, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level.isClientSide && player.getItemInHand(hand).is(Items.SHEARS) && level.getBlockEntity(pos) instanceof BedBlockEntity bedBlockEntity) {
            String playerName = ((BedBlockEntityAccess) bedBlockEntity).getLastPlayerSleptName();

            if (playerName != null) {
                ItemStack hair = new ItemStack(EvoodooersItems.HAIR_TUFT.get());
                hair.getOrCreateTag().putString("HairOwner", playerName);
                player.addItem(hair);
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.getItemInHand(hand).hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(hand));

                ((BedBlockEntityAccess) bedBlockEntity).setLastSleptPlayer(null);

                player.displayClientMessage(Component.translatable("block.evoodooers.bed.hair", playerName), true);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                player.displayClientMessage(Component.translatable("block.evoodooers.bed.no_hair"), true);
                cir.setReturnValue(InteractionResult.CONSUME);
            }
        }
    }

    @Inject(method = "use", at = @At("RETURN"))
    private void evoodooers_setPlayerSlept(BlockState $$0, Level level, BlockPos pos, Player player, InteractionHand $$4, BlockHitResult $$5, CallbackInfoReturnable<InteractionResult> cir) {
        if (cir.getReturnValue().equals(InteractionResult.SUCCESS) && level.getBlockEntity(pos) instanceof BedBlockEntity bedBlockEntity) {
            ((BedBlockEntityAccess) bedBlockEntity).setLastSleptPlayer(player);
        }
    }
}
