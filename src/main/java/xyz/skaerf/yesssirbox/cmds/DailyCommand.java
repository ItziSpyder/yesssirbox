package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.time.Duration;
import java.util.List;

public class DailyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot the daily because player the are you not\nThank you have a nice day");
            return true;
        }
        Player player = (Player) sender;
        if (Yesssirbox.getDailies().get(player.getUniqueId()) != null) {
            Long time = System.currentTimeMillis()-Yesssirbox.getDailies().get(player.getUniqueId());
            Duration duration = Duration.ofMillis(86400000-time);
            long seconds = duration.getSeconds();
            long HH = seconds / 3600;
            long MM = (seconds % 3600) / 60;
            long SS = seconds % 60;
            String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
            if (time <= 86400000) {
                // twenty for of hour
                player.sendMessage(ChatColor.RED+"Please wait 24 hours before running this command again! You have "+timeInHHMMSS+" remaining.");
                return true;
            }
        }
        // do the actual stuff here - run every cmd in a list from config via console dispatchCommand()
        List<String> cmds = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("dailyCommands");
        if (cmds.isEmpty()) {
            player.sendMessage(ChatColor.RED+"There are no daily commands for today! Please check back later.");
            return true;
        }
        for (String command : cmds) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        }
        Yesssirbox.getDailies().put(player.getUniqueId(), System.currentTimeMillis());
        return true;
    }
}
