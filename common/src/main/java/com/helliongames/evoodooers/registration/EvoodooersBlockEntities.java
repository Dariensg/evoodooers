package com.helliongames.evoodooers.registration;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.entity.block.VoodooDollBlockEntity;
import com.helliongames.evoodooers.registration.util.RegistrationProvider;
import com.helliongames.evoodooers.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EvoodooersBlockEntities {
    /**
     * The provider for block entities
     */
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<VoodooDollBlockEntity>> VOODOO_DOLL = BLOCK_ENTITIES.register("voodoo_doll", () -> BlockEntityType.Builder.of(VoodooDollBlockEntity::new, EvoodooersBlocks.VOODOO_DOLL.get()).build(null));

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
