package net.abyssdev.abysschatcolor.menu;

import net.abyssdev.abysschatcolor.AbyssChatColor;
import net.abyssdev.abysschatcolor.menu.item.ColorItem;
import net.abyssdev.abysschatcolor.menu.item.ToggleItem;
import net.abyssdev.abysschatcolor.player.ColorPlayer;
import net.abyssdev.abysslib.builders.ItemBuilder;
import net.abyssdev.abysslib.menu.MenuBuilder;
import net.abyssdev.abysslib.menu.item.MenuItem;
import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.WordUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public final class ColorMenu extends AbyssMenu {

    private final AbyssChatColor plugin;
    private final MenuItem reset;
    private final ToggleItem bold, italic;
    private final ColorItem[] colorItems;

    public ColorMenu(final AbyssChatColor plugin) {
        super(plugin.getConfig(), "menus.color-menu.");

        this.plugin = plugin;

        this.reset = new MenuItem(
                new ItemBuilder(plugin.getConfig(), "menus.color-menu.reset").parse(),
                plugin.getConfig().getInt("menus.color-menu.reset.slot"));

        this.bold = new ToggleItem(
                new ItemBuilder(plugin.getConfig(), "menus.color-menu.bold.enabled").parse(),
                new ItemBuilder(plugin.getConfig(), "menus.color-menu.bold.disabled").parse(),
                plugin.getConfig().getString("menus.color-menu.bold.permission"),
                plugin.getConfig().getInt("menus.color-menu.bold.slot"));

        this.italic = new ToggleItem(
                new ItemBuilder(plugin.getConfig(), "menus.color-menu.italic.enabled").parse(),
                new ItemBuilder(plugin.getConfig(), "menus.color-menu.italic.disabled").parse(),
                plugin.getConfig().getString("menus.color-menu.italic.permission"),
                plugin.getConfig().getInt("menus.color-menu.italic.slot"));

        final ConfigurationSection section = plugin.getConfig().getConfigurationSection("menus.color-menu.color-items");

        this.colorItems = new ColorItem[section.getKeys(false).size()];

        int index = 0;
        for (final String key : section.getKeys(false)) {
            this.colorItems[index] = new ColorItem(
                    new ItemBuilder(plugin.getConfig(), "menus.color-menu.color-items." + key).parse(),
                    key,
                    plugin.getConfig().getString("menus.color-menu.color-items." + key + ".color"),
                    plugin.getConfig().getString("menus.color-menu.color-items." + key + ".permission"),
                    plugin.getConfig().getInt("menus.color-menu.color-items." + key + ".slot"));

            index++;
        }
    }

    @Override
    public void open(final Player player) {

        final ColorPlayer profile = this.plugin.getStorage().get(player.getUniqueId());
        final MenuBuilder builder = this.createBase();

        for (final ColorItem item : this.colorItems) {
            builder.setItem(item.getSlot(), item.getItem());
            builder.addClickEvent(item.getSlot(), event -> {

                player.closeInventory();

                if (!player.hasPermission(item.getPermission())) {
                    this.plugin.getMessageCache().sendMessage(player, "messages.no-permission");
                    return;
                }

                profile.setColor(item.getColor());

                this.plugin.getMessageCache().sendMessage(player, "messages.set-color", new PlaceholderReplacer()
                        .addPlaceholder("%color%", WordUtils.formatText(item.getName()
                                .replace("-", " ")
                                .replace("_", " "))));

            });
        }

        builder.setItem(this.reset.getSlot(), this.reset.getItem());
        builder.setItem(this.bold.getSlot(), profile.isBold() ? this.bold.getEnabled() : this.bold.getDisabled());
        builder.setItem(this.italic.getSlot(), profile.isItalic() ? this.italic.getEnabled() : this.italic.getDisabled());

        builder.addClickEvent(this.reset.getSlot(), event -> {
            profile.setColorName("None");
            profile.setColor(this.plugin.getDefaultColor());
            this.plugin.getMessageCache().sendMessage(player, "messages.reset-color");
        });

        builder.addClickEvent(this.bold.getSlot(), event -> {
            if (!player.hasPermission(this.bold.getPermission())) {
                this.plugin.getMessageCache().sendMessage(player, "messages.no-permission");
                player.closeInventory();
                return;
            }

            profile.setBold(!profile.isBold());
            this.open(player);
        });

        builder.addClickEvent(this.italic.getSlot(), event -> {
            if (!player.hasPermission(this.italic.getPermission())) {
                this.plugin.getMessageCache().sendMessage(player, "messages.no-permission");
                player.closeInventory();
                return;
            }

            profile.setItalic(!profile.isItalic());
            this.open(player);
        });

        player.openInventory(builder.build());
    }
}
