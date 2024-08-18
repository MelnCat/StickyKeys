package dev.melncat.stickykeys.config;

import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class StickyKeysConfig {
	public static ConfigClassHandler<StickyKeysConfig> HANDLER = ConfigClassHandler.createBuilder(StickyKeysConfig.class)
		.id(new ResourceLocation("mymod", "my_config"))
		.serializer(config -> GsonConfigSerializerBuilder.create(config)
			.setPath(Platform.getConfigFolder().resolve("my_mod.json5"))
			.appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
			.setJson5(true)
			.build())
		.build();

	public static Screen createScreen(Screen parent) {
		return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) ->
			builder
				.title(Component.translatable("stickykeys.config.title"))
				.category(ConfigCategory.createBuilder()
					.name(Component.translatable("stickykeys.config.category.main"))
					.option(Option.<Boolean>createBuilder()
						.name(Component.translatable("stickykeys.config.option.default_detach.title"))
						.description(OptionDescription.of(Component.translatable("stickykeys.config.option.default_detach.description")))
						.binding(defaults.detachByDefault, () -> config.detachByDefault, newVal -> config.detachByDefault = newVal)
						.controller(TickBoxControllerBuilder::create)
						.build())
					.build()
				)
		).generateScreen(parent);
	}

	@SerialEntry
	public boolean detachByDefault = false;
}
