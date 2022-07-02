package net.abyssdev.abysschatcolor;

import lombok.Getter;
import net.abyssdev.abysschatcolor.command.ColorCommand;
import net.abyssdev.abysschatcolor.listeners.JoinListener;
import net.abyssdev.abysschatcolor.menu.ColorMenu;
import net.abyssdev.abysschatcolor.placeholder.ColorPlaceholderExpansion;
import net.abyssdev.abysschatcolor.player.ColorPlayer;
import net.abyssdev.abysschatcolor.storage.ChatColorStorage;
import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.storage.json.JsonStorage;
import net.abyssdev.abysslib.text.MessageCache;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

@Getter
public final class AbyssChatColor extends AbyssPlugin {

    private final FileConfiguration colorConfig = this.getConfig("chat-color");

    private final String defaultColor = this.getColorConfig().getString("settings.default-color");
    private final MessageCache messageCache = new MessageCache(this.getColorConfig());

    private final JsonStorage<UUID, ColorPlayer> storage = new ChatColorStorage(this);
    private final ColorCommand command = new ColorCommand(this);

    private AbyssMenu colorMenu;

    @Override
    public void onEnable() {
        this.loadMessages(this.messageCache, this.colorConfig);

        this.colorMenu = new ColorMenu(this);
        this.command.register();

        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ColorPlaceholderExpansion(this).register();
        }

        new JoinListener(this);
    }

    @Override
    public void onDisable() {
        this.storage.write();
        this.command.unregister();
    }

}