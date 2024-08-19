package dev.melncat.stickykeys.cat;

import dev.isxander.yacl3.api.NameableEnum;
import dev.melncat.stickykeys.StickyKeys;
import dev.melncat.stickykeys.mixin.GuiGraphicsInvoker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class CatRenderer {
	private static final ResourceLocation CAT_TEXTURE_AWAKE = ResourceLocation.tryBuild(StickyKeys.MOD_ID, "textures/cat/awake.png");
	private static final ResourceLocation CAT_TEXTURE_ASLEEP = ResourceLocation.tryBuild(StickyKeys.MOD_ID, "textures/cat/asleep.png");

	private static final int FADE_TIME = 10;

	private int fadeTimeLeft = 0;

	private CatRenderer() {
	}

	public static CatRenderer catRenderer() {
		return new CatRenderer();
	}

	// Dependency injection is the root of all good
	public void render(boolean enabled, GuiGraphics graphics, Size size, Position position, int screenWidth, int screenHeight) {
		// todo
		int width = size.getWidth();
		int height = size.getHeight();

		int x = position.calculateX(width, screenWidth);
		int y = position.calculateY(height, screenHeight);

		if (enabled) {
			graphics.blit(CAT_TEXTURE_AWAKE, x, y, 0, 0, width, height, width, height);
			fadeTimeLeft = FADE_TIME;
		} else {
			if (fadeTimeLeft <= 0) return;
			fadeTimeLeft--;
			((GuiGraphicsInvoker) graphics).invokeInnerBlit(
				CAT_TEXTURE_ASLEEP,
				x,
				x + width,
				y,
				y + height,
				0,
				0,
				1,
				0,
				1,
				1,
				1,
				1,
				fadeTimeLeft / ((float) FADE_TIME)
			);
		}
	}


	public enum Size implements NameableEnum {
		MASSIVE(212, 187),
		LARGE(96, 85),
		MEDIUM(77, 68),
		SMALL(58, 51),
		TINY(20, 17),
		MICROSCOPIC(2, 2);

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
		private static final PositionCalculator MIN_PAD = (cat, screen) -> 40;
		private static final PositionCalculator CENTER = (cat, screen) -> screen / 2 - cat / 2;
		private static final PositionCalculator MAX = (cat, screen) -> screen - cat;
	}

	public enum Position implements NameableEnum {
		TOP_LEFT(PositionCalculators.MIN, PositionCalculators.MIN_PAD),
		TOP_CENTER(PositionCalculators.CENTER, PositionCalculators.MIN_PAD),
		TOP_RIGHT(PositionCalculators.MAX, PositionCalculators.MIN_PAD),
		CENTER_LEFT(PositionCalculators.MIN, PositionCalculators.CENTER),
		CENTER_CENTER(PositionCalculators.CENTER, PositionCalculators.CENTER),
		CENTER_RIGHT(PositionCalculators.MAX, PositionCalculators.CENTER),
		BOTTOM_LEFT(PositionCalculators.MIN, PositionCalculators.MAX),
		BOTTOM_CENTER(PositionCalculators.CENTER, PositionCalculators.MAX),
		BOTTOM_RIGHT(PositionCalculators.MAX, PositionCalculators.MAX),
		;


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
