package dev.melncat.stickykeys;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.melncat.stickykeys.mixin.KeyMappingAccessor;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public final class StickyKeys {
	public static final String MOD_ID = "stickykeys";

	public static final KeyMapping HOLD_KEYS_MAPPING = new KeyMapping(
		"key.stickykeys.stick_keys",
		InputConstants.Type.KEYSYM,
		InputConstants.KEY_K,
		"category.stickykeys"
	);

	public static void init() {
		registerKeyMappings();
		ClientTickEvent.CLIENT_POST.register(minecraft -> {
			while (HOLD_KEYS_MAPPING.consumeClick()) {
				List<KeyMapping> keys = Arrays.stream(Minecraft.getInstance().options.keyMappings)
					.filter(x -> x != HOLD_KEYS_MAPPING && ((KeyMappingAccessor) x).getIsDown())
					.toList();
				HeldKeyManager.getInstance().setHeldKeys(keys);
			}
		});
		ClientGuiEvent.RENDER_HUD.register((graphics, delta) -> {
			if (!HeldKeyManager.getInstance().isEnabled()) return;
			Minecraft minecraft = Minecraft.getInstance();
			graphics.drawCenteredString(
				minecraft.font,
				HeldKeyManager.getInstance().getHoldMessage(),
				minecraft.getWindow().getGuiScaledWidth() / 2,
				20,
				0xffffff
			);
		});

	}

	public static void registerKeyMappings() {
		KeyMappingRegistry.register(HOLD_KEYS_MAPPING);
	}

}
