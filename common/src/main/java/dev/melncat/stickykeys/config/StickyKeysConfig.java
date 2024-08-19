package dev.melncat.stickykeys.config;

import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumDropdownControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.melncat.stickykeys.cat.CatRenderer;
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
						.build()
					)
					.option(Option.<Boolean>createBuilder()
						.name(Component.translatable("stickykeys.config.option.shift_protection.title"))
						.description(OptionDescription.of(Component.translatable("stickykeys.config.option.shift_protection.description")))
						.binding(defaults.shiftProtection, () -> config.shiftProtection, newVal -> config.shiftProtection = newVal)
						.controller(TickBoxControllerBuilder::create)
						.build()
					)
					.group(OptionGroup.createBuilder()
						.name(Component.translatable("stickykeys.config.group.cat.title"))
						.description(OptionDescription.of(Component.translatable("stickykeys.config.group.cat.description")))
						.collapsed(true)
						.option(Option.<Boolean>createBuilder()
							.name(Component.translatable("stickykeys.config.option.cat.enabled.title"))
							.description(OptionDescription.of(Component.translatable("stickykeys.config.option.cat.enabled.description")))
							.binding(defaults.enableCat, () -> config.enableCat, newVal -> config.enableCat = newVal)
							.controller(TickBoxControllerBuilder::create)
							.build()
						)
						.option(Option.<CatRenderer.Size>createBuilder()
							.name(Component.translatable("stickykeys.config.option.cat.size.title"))
							.description(OptionDescription.of(Component.translatable("stickykeys.config.option.cat.size.description")))
							.binding(defaults.catSize, () -> config.catSize, newVal -> config.catSize = newVal)
							.controller(EnumDropdownControllerBuilder::create)
							.build()
						)
						.option(Option.<CatRenderer.Position>createBuilder()
							.name(Component.translatable("stickykeys.config.option.cat.position.title"))
							.description(OptionDescription.of(Component.translatable("stickykeys.config.option.cat.position.description")))
							.binding(defaults.catPosition, () -> config.catPosition, newVal -> config.catPosition = newVal)
							.controller(EnumDropdownControllerBuilder::create)
							.build()
						)
						.build()
					)
					.build()
				)
				.save(HANDLER::save)
		).generateScreen(parent);
	}

	@SerialEntry
	public boolean detachByDefault = false;

	@SerialEntry
	public boolean shiftProtection = true;

	// Cat options

	@SerialEntry
	public boolean enableCat = false;

	public CatRenderer.Size catSize = CatRenderer.Size.MEDIUM;

	public CatRenderer.Position catPosition = CatRenderer.Position.TOP_CENTER;
}
