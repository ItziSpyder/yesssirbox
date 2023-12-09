package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.skaerf.yesssirbox.Yesssirbox;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

import java.time.Duration;
import java.util.List;

@CommandName("daily")
public class DailyCommand implements Command {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player player)) {
            error(sender, "You cannot the daily because player the are you not\nThank you have a nice day");
            return;
        }

        if (Yesssirbox.getDailies().get(player.getUniqueId()) != null) {
            long time = System.currentTimeMillis()-Yesssirbox.getDailies().get(player.getUniqueId());
            Duration duration = Duration.ofMillis(86400000-time);
            long seconds = duration.getSeconds();
            long HH = seconds / 3600;
            long MM = (seconds % 3600) / 60;
            long SS = seconds % 60;
            String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
            if (time <= 86400000) {
                // twenty for of hour
                player.sendMessage(ChatColor.RED+"Please wait 24 hours before running this command again! You have "+timeInHHMMSS+" remaining.");
                return;
            }
        }
        // do the actual stuff here - run every cmd in a list from config via console dispatchCommand()
        List<String> cmds = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("dailyCommands");
        if (cmds.isEmpty()) {
            player.sendMessage(ChatColor.RED+"There are no daily commands for today! Please check back later.");
            return;
        }
        for (String command : cmds) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        }
        Yesssirbox.getDailies().put(player.getUniqueId(), System.currentTimeMillis());
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
