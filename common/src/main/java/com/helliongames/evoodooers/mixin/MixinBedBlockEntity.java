package com.helliongames.evoodooers.mixin;

import com.helliongames.evoodooers.access.BedBlockEntityAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

@Mixin(BedBlockEntity.class)
public abstract class MixinBedBlockEntity extends BlockEntity implements BedBlockEntityAccess {
    private String lastSleptPlayer = null;

    public MixinBedBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
        super($$0, $$1, $$2);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.lastSleptPlayer != null) {
            tag.putString("LastSlept", this.lastSleptPlayer);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("LastSlept")) {
            this.lastSleptPlayer = tag.getString("LastSlept");
        }
    }

    @Override
    public void setLastSleptPlayer(@Nullable Player player) {
        if (player != null) {
            this.lastSleptPlayer = player.getName().getString();
        } else {
            this.lastSleptPlayer = null;
        }
    }

    @Override
    public String getLastPlayerSleptName() {
        return this.lastSleptPlayer;
    }
}
