package net.abyssdev.abysschatcolor.command;

import net.abyssdev.abysschatcolor.AbyssChatColor;
import net.abyssdev.abysslib.command.Command;
import net.abyssdev.abysslib.command.context.CommandContext;
import org.bukkit.entity.Player;

import java.util.Arrays;

public final class ColorCommand extends Command<Player> {

    private final AbyssChatColor plugin;

    public ColorCommand(final AbyssChatColor plugin) {
        super("color", "Base color command", Arrays.asList(
                "colors",
                "chatcolor",
                "chat",
                "colorchat"
        ), Player.class);

        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandContext<Player> context) {
        this.plugin.getColorMenu().open(context.getSender());
    }

}