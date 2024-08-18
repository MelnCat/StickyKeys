package dev.melncat.stickykeys.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public int missTime;

	@Shadow @Final
	public Options options;

	// this.continueAttack(this.screen == null && !bl3 && this.options.keyAttack.isDown() && this.mouseHandler.isMouseGrabbed());
	@WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;isMouseGrabbed()Z"))
	private boolean isMouseGrabbed(MouseHandler instance, Operation<Boolean> original) {
		return HeldKeyManager.getInstance().isEnabled() || original.call(instance);
	}
	@Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
	private void keyPress(boolean leftClick, CallbackInfo ci) {
		if (HeldKeyManager.getInstance().isHeld(options.keyAttack)) {
			missTime = 0;
			if (!leftClick) ci.cancel();
		}
	}


}
