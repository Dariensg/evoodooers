package com.helliongames.evoodooers.access;

import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public interface BedBlockEntityAccess {
    void setLastSleptPlayer(@Nullable Player player);

    String getLastPlayerSleptName();
}
