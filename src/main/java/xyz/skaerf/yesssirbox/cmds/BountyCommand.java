package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.List;
import java.util.UUID;

public class BountyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("no");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            List<String> playerBounties = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("bounties");
            if (playerBounties.isEmpty()) {
                player.sendMessage(ChatColor.GREEN+"There are no bounties open at the moment! Check back later, or create your own with "+ChatColor.BOLD +"/bounty <player> <amount> "+ChatColor.GREEN+"!");
            }
            else {
                for (String bounty : playerBounties) {
                    String status = ChatColor.RED+"OFFLINE";
                    if (Bukkit.getPlayer(UUID.fromString(bounty.split(":")[0])) != null) {
                        status = ChatColor.GREEN+"ONLINE";
                    }
                    player.sendMessage(ChatColor.GOLD + "Bounty on " + Bukkit.getOfflinePlayer(UUID.fromString(bounty.split(":")[0])).getName() + " for $" + bounty.split(":")[1] + " - " + status);
                }
            }
        }
        else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED+"That player is not online!");
                return true;
            }
            if (args[1] == null) {
                player.sendMessage(ChatColor.RED+"Please provide an amount for the bounty! e.g. 1000");
                return true;
            }
            if (target.equals(player)) {
                player.sendMessage(ChatColor.RED+"You can't put a bounty on yourself!");
                return true;
            }
            double bounty;
            try {
                bounty = Double.parseDouble(args[1]);
                bounty = Math.floor(bounty*100)/100;
                if (bounty < 0) throw new NumberFormatException();
            }
            catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED+"Please enter a valid number! e.g. 1000");
                return true;
            }
            if (Yesssirbox.econ.getBalance(player) < bounty) {
                player.sendMessage(ChatColor.RED+"You cannot afford that bounty!");
                return true;
            }
            List<String> playerBounties = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("bounties");
            String message = "&c&lyesssirbox &8&l>> &a"+player.getName()+" has just set a bounty of $"+bounty+" on "+target.getName()+"!";
            for (String entry : playerBounties) if (entry.split(":")[0].equals(target.getUniqueId().toString())) {
                double oldAmount = Double.parseDouble(entry.split(":")[1]);
                playerBounties.remove(entry);
                message = message + "\nThe bounty total on "+target.getName()+" is $"+(bounty+oldAmount);
                bounty = bounty+oldAmount;
                break;
            }
            playerBounties.add(target.getUniqueId()+":"+bounty);
            Yesssirbox.getPlugin(Yesssirbox.class).getConfig().set("bounties", playerBounties);
            Yesssirbox.getPlugin(Yesssirbox.class).saveConfig();
            Yesssirbox.getPlugin(Yesssirbox.class).reloadConfig();
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            Yesssirbox.econ.withdrawPlayer(player, bounty);
            player.sendMessage(ChatColor.GREEN+"The bounty has been set and the money has been taken from your account.");
        }
        return true;
    }
}
