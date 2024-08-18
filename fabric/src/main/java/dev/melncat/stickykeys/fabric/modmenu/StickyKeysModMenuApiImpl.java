package dev.melncat.stickykeys.fabric.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.melncat.stickykeys.config.StickyKeysConfig;

public class StickyKeysModMenuApiImpl implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> StickyKeysConfig.HANDLER.generateScreen(parent);
	}


}
