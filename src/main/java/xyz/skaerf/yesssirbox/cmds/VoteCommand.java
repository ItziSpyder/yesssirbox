package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.List;

public class VoteCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("voteMessages") != null) {
            List<String> list = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("voteMessages");
            for (String str : list) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Please ask a staff member for the Vote-Links!");
        }
        return true;
    }
}
