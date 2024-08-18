package dev.melncat.stickykeys;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.KeyMapping;

public final class StickyKeys {
    public static final String MOD_ID = "stickykeys";

    public static final KeyMapping HOLD_KEYS_MAPPING = new KeyMapping(
        "key.stickykeys.stick_keys",
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_SEMICOLON,
        "category.stickykeys"
    );

    public static void init() {
        registerKeyMappings();
        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (HOLD_KEYS_MAPPING.consumeClick()) {

            }
        });
    }

    public static void registerKeyMappings() {
        KeyMappingRegistry.register(HOLD_KEYS_MAPPING);
    }

}
