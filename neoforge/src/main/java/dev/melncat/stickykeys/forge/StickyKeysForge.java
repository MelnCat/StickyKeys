package dev.melncat.stickykeys.forge;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.melncat.stickykeys.StickyKeys;
import dev.melncat.stickykeys.config.StickyKeysConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(StickyKeys.MOD_ID)
public final class StickyKeysForge {
	public StickyKeysForge() {
		if (Platform.getEnvironment() == Env.SERVER) return;

		StickyKeys.init();

		ModLoadingContext.get().registerExtensionPoint(
			IConfigScreenFactory.class,
			() -> (client, parent) -> StickyKeysConfig.createScreen(parent)
		);
	}
}
