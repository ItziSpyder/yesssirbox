package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Vote implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage(ChatColor.GREEN + "yesssirbox Vote-Links:");
        commandSender.sendMessage(ChatColor.GRAY + "1. https://best-minecraft-servers.co/server-yesssirnet.18282");
        commandSender.sendMessage(ChatColor.GRAY + "2. https://minecraftservers.org/server/653485");
        Player p = (Player) commandSender;
        return false;
    }
}
