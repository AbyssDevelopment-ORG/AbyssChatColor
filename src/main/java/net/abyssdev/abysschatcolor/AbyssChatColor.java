package net.abyssdev.abysschatcolor;

import lombok.Getter;
import net.abyssdev.abysschatcolor.command.ColorCommand;
import net.abyssdev.abysschatcolor.menu.ColorMenu;
import net.abyssdev.abysschatcolor.placeholder.ColorPlaceholderExpansion;
import net.abyssdev.abysschatcolor.player.ColorPlayer;
import net.abyssdev.abysschatcolor.storage.ChatColorStorage;
import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.storage.json.JsonStorage;
import net.abyssdev.abysslib.text.MessageCache;

import java.util.UUID;

@Getter
public final class AbyssChatColor extends AbyssPlugin {

    private final String defaultColor = this.getConfig().getString("settings.default-color");
    private final MessageCache messageCache = new MessageCache(this.getConfig());
    private final JsonStorage<UUID, ColorPlayer> storage = new ChatColorStorage(this);
    private final AbyssMenu colorMenu = new ColorMenu(this);
    private final ColorCommand command = new ColorCommand(this);

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadMessages(this.messageCache, this.getConfig());

        this.command.register();

        new ColorPlaceholderExpansion(this).register();
    }

    @Override
    public void onDisable() {
        this.storage.write();
        this.command.unregister();
    }

}