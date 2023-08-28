package xyz.skaerf.yesssirbox.cmds;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AutoCompressCommand implements CommandExecutor {

    public static HashMap<Player, Boolean> autoc = new HashMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Please only Use the Command as a Player");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("syesssirbox.ac")) {
            player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "Sidiju doesn't think you have the permission for that. ;) you can (/buy) it if you want.")));
            return true;
        }
        if (autoc.containsKey(player)){
            autoc.remove(player);
            player.sendMessage(ChatColor.RED + "Turned the AutoCompressor off.");
        }
        else {
            autoc.put(player, true);
            player.sendMessage(ChatColor.GREEN + "Turned the AutoCompressor on.");
        }
        return false;
    }
}
