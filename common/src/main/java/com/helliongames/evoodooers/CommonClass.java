package com.helliongames.evoodooers;

import com.helliongames.evoodooers.registration.EvoodooersBlockEntities;
import com.helliongames.evoodooers.registration.EvoodooersBlocks;
import com.helliongames.evoodooers.registration.EvoodooersItems;
import com.helliongames.evoodooers.registration.EvoodooersRecipes;
import com.helliongames.evoodooers.registration.EvoodooersTabs;

public class CommonClass {
    public static void init() {
        EvoodooersBlocks.loadClass();
        EvoodooersItems.loadClass();
        EvoodooersBlockEntities.loadClass();
        EvoodooersRecipes.loadClass();
        EvoodooersTabs.loadClass();
    }
}