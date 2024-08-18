package dev.melncat.stickykeys.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.melncat.stickykeys.StickyKeys;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyMapping.class)
public class KeyMappingMixin {
	@Shadow private InputConstants.Key key;

	@Inject(method = "isDown", at = @At("HEAD"), cancellable = true)
	private void isDown(CallbackInfoReturnable<Boolean> cir) {
		if (HeldKeyManager.getInstance().isHeld((KeyMapping) (Object) this)) {
			cir.setReturnValue(true);
		}
	}
}
