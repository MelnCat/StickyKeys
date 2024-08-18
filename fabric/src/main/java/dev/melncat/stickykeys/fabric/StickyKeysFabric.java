package dev.melncat.stickykeys.fabric;

import net.fabricmc.api.ClientModInitializer;

import dev.melncat.stickykeys.StickyKeys;

public final class StickyKeysFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        StickyKeys.init();
    }
}
