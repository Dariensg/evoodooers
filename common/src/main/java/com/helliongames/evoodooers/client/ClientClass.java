package com.helliongames.evoodooers.client;

import com.helliongames.evoodooers.platform.Services;

public class ClientClass {
    public static void init() {
        Services.CLIENT_HELPER.registerModelLayers();
        Services.CLIENT_HELPER.registerEntityRenderers();
    }
}
