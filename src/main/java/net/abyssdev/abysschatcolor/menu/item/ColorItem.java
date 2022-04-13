package net.abyssdev.abysschatcolor.menu.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public final class ColorItem {

    private final ItemStack item;
    private final String name, color, permission;
    private final int slot;

}