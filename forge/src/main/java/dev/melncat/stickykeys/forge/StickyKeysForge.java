package dev.melncat.stickykeys.forge;

import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import dev.melncat.stickykeys.StickyKeys;

@Mod(StickyKeys.MOD_ID)
public final class StickyKeysForge {
    public StickyKeysForge() {
        if (Platform.getEnvironment() == Env.SERVER) return;

        EventBuses.registerModEventBus(StickyKeys.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        StickyKeys.init();
    }
}
