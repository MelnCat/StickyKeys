package dev.melncat.stickykeys.forge.mixin;

import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToggleKeyMapping.class)
public abstract class ToggleKeyMappingMixin {
	@Inject(method = "isDown", at = @At("HEAD"), cancellable = true)
	private void isDown(CallbackInfoReturnable<Boolean> cir) {
		if (HeldKeyManager.getInstance().isHeld((KeyMapping) (Object) this) && !HeldKeyManager.getInstance().isChecking()) {
			cir.setReturnValue(true);
		}
	}
}
