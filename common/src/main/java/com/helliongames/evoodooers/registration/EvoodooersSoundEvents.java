package com.helliongames.evoodooers.registration;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.registration.util.RegistrationProvider;
import com.helliongames.evoodooers.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class EvoodooersSoundEvents {
    /**
     * The provider for sound events
     */
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, Constants.MOD_ID);

    public static final RegistryObject<SoundEvent> DAY = SOUND_EVENTS.register("record.day", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Constants.MOD_ID, "record.day")));

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
