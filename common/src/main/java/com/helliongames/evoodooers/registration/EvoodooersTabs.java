package com.helliongames.evoodooers.registration;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.registration.util.RegistrationProvider;
import com.helliongames.evoodooers.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EvoodooersTabs {
    /**
     * The provider for creative tabs
     */
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EVOODOOERS_TAB = TABS.register("evoodooers", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("itemGroup.evoodooers_tab")).icon(() -> new ItemStack(EvoodooersItems.VOODOO_DOLL.get())).displayItems((itemDisplayParameters, output) -> {
        output.accept(EvoodooersItems.VOODOO_DOLL.get());
        output.accept(EvoodooersItems.HAIR_TUFT.get());
    }).build());

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
