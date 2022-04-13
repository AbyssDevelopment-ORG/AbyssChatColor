package net.abyssdev.abysschatcolor.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.abyssdev.abysschatcolor.AbyssChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class ColorPlaceholderExpansion extends PlaceholderExpansion {

    private final AbyssChatColor plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "AbyssChatColor";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Relocation";
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(final OfflinePlayer player, @NotNull final String params) {
        return this.plugin.getStorage().get(player.getUniqueId()).getColor();
    }
}
