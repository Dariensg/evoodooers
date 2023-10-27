package com.helliongames.evoodooers.client;

import net.fabricmc.api.ClientModInitializer;

public class EvoodooersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientClass.init();
    }
}
