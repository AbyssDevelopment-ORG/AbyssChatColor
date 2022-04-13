package net.abyssdev.abysschatcolor.storage;

import net.abyssdev.abysschatcolor.AbyssChatColor;
import net.abyssdev.abysschatcolor.player.ColorPlayer;
import net.abyssdev.abysslib.storage.json.JsonStorage;
import net.abyssdev.abysslib.utils.file.Files;

import java.util.UUID;

public final class ChatColorStorage extends JsonStorage<UUID, ColorPlayer> {

    private final AbyssChatColor plugin;

    public ChatColorStorage(final AbyssChatColor plugin) {
        super(Files.file("data.json", plugin), ColorPlayer.class);
        this.plugin = plugin;
    }

    @Override
    public ColorPlayer constructValue(final UUID key) {
        return new ColorPlayer(this.plugin, key);
    }
}
