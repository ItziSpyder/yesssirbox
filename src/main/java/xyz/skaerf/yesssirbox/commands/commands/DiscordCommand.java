package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xyz.skaerf.yesssirbox.Yesssirbox;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

@CommandName("discord")
public class DiscordCommand implements Command {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getString("discordMessage") != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getString("discordMessage")));
        }
        else {
            sender.sendMessage(ChatColor.GREEN+"Please ask a staff member for a link to the Discord server!");
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
