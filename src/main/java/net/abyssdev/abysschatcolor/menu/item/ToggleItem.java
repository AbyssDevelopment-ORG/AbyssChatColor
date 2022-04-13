package net.abyssdev.abysschatcolor.menu.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public final class ToggleItem {

    private final ItemStack enabled, disabled;
    private final String permission;
    private final int slot;

}
