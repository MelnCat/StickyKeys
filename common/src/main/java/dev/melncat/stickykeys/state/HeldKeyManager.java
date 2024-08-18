package dev.melncat.stickykeys.state;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HeldKeyManager {
	private final Set<KeyMapping> heldKeys = new HashSet<>();
	private Component holdMessage = Component.empty();

	private static final HeldKeyManager INSTANCE = new HeldKeyManager();

	private HeldKeyManager() {
	}

	public boolean isHeld(KeyMapping key) {
		return heldKeys.contains(key);
	}

	public boolean isEnabled() {
		return !heldKeys.isEmpty();
	}

	public void setHeldKeys(Collection<KeyMapping> keys) {
		heldKeys.clear();
		heldKeys.addAll(keys);
		if (keys.isEmpty()) holdMessage = Component.empty();
		else {
			MutableComponent keysList = Component.empty();
			boolean first = true;
			for (Component key : keys.stream().map(x -> x.getTranslatedKeyMessage()).distinct().toList()) {
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
}
