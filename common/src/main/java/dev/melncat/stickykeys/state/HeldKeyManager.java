package dev.melncat.stickykeys.state;

import net.minecraft.client.KeyMapping;

import java.util.HashSet;
import java.util.Set;

public class HeldKeyManager {
	private final Set<KeyMapping> heldKeys = new HashSet<>();

	private static final HeldKeyManager INSTANCE = new HeldKeyManager();

	private HeldKeyManager() {}

	public boolean isHeld(KeyMapping key) {
		return heldKeys.contains(key);
	}

	public boolean isEnabled() {
		return !heldKeys.isEmpty();
	}

	public static HeldKeyManager getInstance() {
		return INSTANCE;
	}
}
