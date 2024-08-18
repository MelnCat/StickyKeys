package dev.melncat.stickykeys.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import dev.melncat.stickykeys.StickyKeys;

@Mod(StickyKeys.MOD_ID)
public final class StickyKeysForge {
    public StickyKeysForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(StickyKeys.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        StickyKeys.init();
    }
}
