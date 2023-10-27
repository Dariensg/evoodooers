package com.helliongames.evoodooers.entity.block;

import com.helliongames.evoodooers.registration.EvoodooersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class VoodooDollBlockEntity extends BlockEntity {
    public VoodooDollBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public VoodooDollBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(EvoodooersBlockEntities.VOODOO_DOLL.get(), blockPos, blockState);
    }
}
