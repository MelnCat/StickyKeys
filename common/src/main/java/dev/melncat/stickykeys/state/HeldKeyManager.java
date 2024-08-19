package dev.melncat.stickykeys.state;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HeldKeyManager {
	private final Set<KeyMapping> heldKeys = new HashSet<>();
	private int shiftProtectionTime = 0;

	private Component holdMessage = Component.empty();
	private boolean checking = false;

	private static final HeldKeyManager INSTANCE = new HeldKeyManager();

	private HeldKeyManager() {
	}

	public boolean isHeld(KeyMapping key) {
		return heldKeys.contains(key) || (shiftProtectionTime > 0 && key == Minecraft.getInstance().options.keyShift);
	}

	public boolean isEnabled() {
		return !heldKeys.isEmpty();
	}

	public void tickShiftProtection() {
		if (shiftProtectionTime > 0) shiftProtectionTime--;
	}

	public void setHeldKeys(Collection<KeyMapping> keys) {
		if (heldKeys.contains(Minecraft.getInstance().options.keyShift) && !keys.contains(Minecraft.getInstance().options.keyShift)) {
			shiftProtectionTime = 10;
		}
		heldKeys.clear();
		heldKeys.addAll(keys);
		if (keys.isEmpty()) holdMessage = Component.empty();
		else {
			MutableComponent keysList = Component.empty();
			boolean first = true;
			for (Component key : keys.stream().map(x -> Component.literal(x.getName())).distinct().toList()) {
				if (!first) keysList.append(Component.literal(", ").withStyle(ChatFormatting.GRAY));
				else first = false;
				keysList.append(key.copy().withStyle(ChatFormatting.WHITE));
			}
			holdMessage = Component.translatable("stickykeys.hud.currently_holding", keysList).withStyle(ChatFormatting.YELLOW);
		}
	}

	public static HeldKeyManager getInstance() {
		return INSTANCE;
	}

	public void clear() {
		heldKeys.clear();
		holdMessage = Component.empty();
	}

	public Component getHoldMessage() {
		return holdMessage;
	}

	public boolean isChecking() {
		return checking;
	}

	public void setChecking(boolean checking) {
		this.checking = checking;
	}
}
