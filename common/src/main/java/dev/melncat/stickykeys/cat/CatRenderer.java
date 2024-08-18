package dev.melncat.stickykeys.cat;

import dev.isxander.yacl3.api.NameableEnum;
import dev.melncat.stickykeys.StickyKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CatRenderer {
	private static final ResourceLocation CAT_TEXTURE_AWAKE = ResourceLocation.tryBuild(StickyKeys.MOD_ID, "cat/awake.png");
	private static final ResourceLocation CAT_TEXTURE_ASLEEP = ResourceLocation.tryBuild(StickyKeys.MOD_ID, "cat/asleep.png");

	private CatRenderer() {}

	public static CatRenderer catRenderer() { return new CatRenderer(); }

	// Dependency injection is the root of all good
	public void render(boolean enabled, GuiGraphics graphics, Size size, Position position, int screenWidth, int screenHeight) {
		// todo
		int width = size.getWidth();
		int height = size.getHeight();

		int x = position.calculateX(width, screenWidth);
		int y = position.calculateY(height, screenHeight);

		graphics.blit(CAT_TEXTURE_AWAKE, x, y, 0, 0, width, height);
	}

	public enum Size implements NameableEnum {
		MASSIVE(0, 0),
		LARGE(0, 0),
		MEDIUM(0, 0),
		SMALL(0, 0),
		TINY(0, 0),
		MICROSCOPIC(0, 0);

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		private final int width;
		private final int height;

		Size(int width, int height) {
			this.width = width;
			this.height = height;
		}

		@Override
		public Component getDisplayName() {
			return Component.translatable("stickykeys.cat.size." + name().toLowerCase());
		}
	}

	@FunctionalInterface
	private interface PositionCalculator {
		int calculate(int catSize, int screenSize);
	}

	private static class PositionCalculators {
		private static final PositionCalculator MIN = (cat, screen) -> 0;
		private static final PositionCalculator CENTER = (cat, screen) -> screen / 2 - cat / 2;
		private static final PositionCalculator MAX = (cat, screen) -> screen - cat;
	}

	public enum Position implements NameableEnum {
		TOP_LEFT(PositionCalculators.MIN, PositionCalculators.MIN),
		TOP_CENTER(PositionCalculators.CENTER, PositionCalculators.MIN),
		TOP_RIGHT(PositionCalculators.MAX, PositionCalculators.MIN),
		CENTER_LEFT(PositionCalculators.MIN, PositionCalculators.CENTER),
		CENTER_CENTER(PositionCalculators.CENTER, PositionCalculators.CENTER),
		CENTER_RIGHT(PositionCalculators.MAX, PositionCalculators.CENTER),
		BOTTOM_LEFT(PositionCalculators.MIN, PositionCalculators.MAX),
		BOTTOM_CENTER(PositionCalculators.CENTER, PositionCalculators.MAX),
		BOTTOM_RIGHT(PositionCalculators.MAX, PositionCalculators.MAX),;


		private final PositionCalculator calculateX;
		private final PositionCalculator calculateY;

		Position(PositionCalculator calculateX, PositionCalculator calculateY) {
			this.calculateX = calculateX;
			this.calculateY = calculateY;
		}

		public int calculateX(int catWidth, int screenWidth) {
			return calculateX.calculate(catWidth, screenWidth);
		}
		public int calculateY(int catHeight, int screenHeight) {
			return calculateY.calculate(catHeight, screenHeight);
		}

		@Override
		public Component getDisplayName() {
			return Component.translatable("stickykeys.cat.position." + name().toLowerCase());
		}
	}
}
