package dev.melncat.stickykeys.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.melncat.stickykeys.config.StickyKeysConfig;
import dev.melncat.stickykeys.state.HeldKeyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
	@WrapWithCondition(method = "onPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;grabMouse()V"))
	private boolean keyPress(MouseHandler instance) {
		return !(StickyKeysConfig.HANDLER.instance().detachByDefault && HeldKeyManager.getInstance().isEnabled());
	}
}
