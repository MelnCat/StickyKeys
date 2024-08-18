package dev.melncat.stickykeys.mixin;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
	@Accessor
	boolean getIsDown();
}