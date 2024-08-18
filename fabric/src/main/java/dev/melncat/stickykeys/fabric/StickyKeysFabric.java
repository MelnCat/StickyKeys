package dev.melncat.stickykeys.fabric;

import net.fabricmc.api.ClientModInitializer;

import dev.melncat.stickykeys.StickyKeys;

public final class StickyKeysFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        StickyKeys.init();
    }
}
