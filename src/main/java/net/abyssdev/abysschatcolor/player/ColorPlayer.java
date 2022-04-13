package net.abyssdev.abysschatcolor.player;

import lombok.Getter;
import lombok.Setter;
import net.abyssdev.abysschatcolor.AbyssChatColor;
import net.abyssdev.abysslib.storage.id.Id;

import java.util.UUID;

@Getter
@Setter
public final class ColorPlayer {

    @Id
    private final UUID uuid;

    private String color, colorName;
    private boolean bold, italic;

    public ColorPlayer(final AbyssChatColor plugin, final UUID uuid) {
        this.uuid = uuid;
        this.color = plugin.getDefaultColor();
        this.colorName = "None";
    }

    public String getColor() {
        if (this.bold && this.italic) {
            return this.color + "&l&o";
        }

        if (this.bold) {
            return this.color + "&l";
        }

        if (this.italic) {
            return this.color + "&o";
        }

        return this.color;
    }

}