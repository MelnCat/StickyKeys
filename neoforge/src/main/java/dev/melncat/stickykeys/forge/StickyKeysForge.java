package dev.melncat.stickykeys.forge;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.melncat.stickykeys.StickyKeys;
import dev.melncat.stickykeys.config.StickyKeysConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

@Mod(StickyKeys.MOD_ID)
public final class StickyKeysForge {
	public StickyKeysForge() {
		if (Platform.getEnvironment() == Env.SERVER) return;

		StickyKeys.init();

		ModLoadingContext.get().registerExtensionPoint(
			IConfigScreenFactory.class,
			() -> new IConfigScreenFactory() {

				@Override
				public @NotNull Screen createScreen(Minecraft client, Screen parent) {
					return StickyKeysConfig.createScreen(parent);
				}

				// For 1.21.1 neoforge
				public @NotNull Screen createScreen(ModContainer container, Screen parent) {
					return StickyKeysConfig.createScreen(parent);
				}
			}
		);
	}
}
