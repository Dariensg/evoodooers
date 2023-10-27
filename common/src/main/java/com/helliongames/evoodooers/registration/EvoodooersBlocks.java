package com.helliongames.evoodooers.registration;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.block.VoodooDollBlock;
import com.helliongames.evoodooers.registration.util.RegistrationProvider;
import com.helliongames.evoodooers.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EvoodooersBlocks {

    /**
     * The provider for blocks
     */
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

    public static final RegistryObject<Block> VOODOO_DOLL = BLOCKS.register("voodoo_doll", () -> new VoodooDollBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
