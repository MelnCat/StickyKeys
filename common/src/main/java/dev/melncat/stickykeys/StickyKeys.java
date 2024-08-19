package dev.melncat.stickykeys;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.melncat.stickykeys.cat.CatRenderer;
import dev.melncat.stickykeys.config.StickyKeysConfig;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

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

	public static final KeyMapping DETACH_CURSOR_MAPPING = new KeyMapping(
		"key.stickykeys.detach_cursor",
		InputConstants.Type.KEYSYM,
		InputConstants.KEY_SEMICOLON,
		"category.stickykeys"
	);

	private static final CatRenderer catRenderer = CatRenderer.catRenderer();

	public static void init() {
		registerKeyMappings();
		StickyKeysConfig.HANDLER.load();
		StickyKeysConfig config = StickyKeysConfig.HANDLER.instance();
		ClientTickEvent.CLIENT_POST.register(minecraft -> {
			HeldKeyManager.getInstance().tickShiftProtection();
			while (HOLD_KEYS_MAPPING.consumeClick()) {
				HeldKeyManager.getInstance().setChecking(false);
				List<KeyMapping> keys = Arrays.stream(Minecraft.getInstance().options.keyMappings)
					.filter(x -> x != HOLD_KEYS_MAPPING && x.isDown())
					.toList();
				HeldKeyManager.getInstance().setHeldKeys(keys);
				if (!keys.isEmpty() && config.detachByDefault) Minecraft.getInstance().mouseHandler.releaseMouse();
			}
			while (DETACH_CURSOR_MAPPING.consumeClick()) {
				Minecraft.getInstance().mouseHandler.releaseMouse();
			}
		});
		ClientGuiEvent.RENDER_HUD.register((graphics, delta) -> {
			Minecraft minecraft = Minecraft.getInstance();
			if (config.enableCat)
				catRenderer.render(
					HeldKeyManager.getInstance().isEnabled(),
					graphics,
					config.catSize,
					config.catPosition,
					minecraft.getWindow().getGuiScaledWidth(),
					minecraft.getWindow().getGuiScaledHeight()
				);
			if (!HeldKeyManager.getInstance().isEnabled()) return;
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
