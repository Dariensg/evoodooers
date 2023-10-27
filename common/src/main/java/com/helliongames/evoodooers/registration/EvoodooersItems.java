package com.helliongames.evoodooers.registration;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.item.HairTuftItem;
import com.helliongames.evoodooers.item.VoodooDollBlockItem;
import com.helliongames.evoodooers.registration.util.RegistrationProvider;
import com.helliongames.evoodooers.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class EvoodooersItems {

    /**
     * The provider for items
     */
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<HairTuftItem> HAIR_TUFT = ITEMS.register("hair_tuft", () -> new HairTuftItem(new Item.Properties()));
    public static final RegistryObject<VoodooDollBlockItem> VOODOO_DOLL = ITEMS.register("voodoo_doll", () -> new VoodooDollBlockItem(EvoodooersBlocks.VOODOO_DOLL.get(), new Item.Properties()));

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
