package dev.melncat.stickykeys.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;pauseGame(Z)V"))
	private boolean render(Minecraft instance, boolean b) {
		return !HeldKeyManager.getInstance().isEnabled();
	}
}
