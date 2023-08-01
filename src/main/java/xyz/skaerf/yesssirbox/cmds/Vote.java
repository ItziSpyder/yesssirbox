package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.skaerf.yesssirbox.Yesssirbox;

public class Vote implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getString("voteMessage") != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getString("voteMessage")));
        }
        else {
            sender.sendMessage(ChatColor.RED + "Please ask a staff member for the Vote-Links!");
        }
        return true;
    }
}
