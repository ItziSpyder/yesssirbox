package xyz.skaerf.yesssirbox.cmds;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BountyCommand implements CommandExecutor {

    private HashMap<Player, Long> coolDown = new HashMap<>();

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
                player.sendMessage(Component.text(ChatColor.GREEN+"Showing bounties for online players only."));
                for (String bounty : playerBounties) {
                    Player target = Bukkit.getPlayer(UUID.fromString(bounty.split(":")[0]));
                    if (target != null && !Yesssirbox.isVanished(target)) {
                        player.sendMessage(ChatColor.GOLD + "Bounty on " + Bukkit.getOfflinePlayer(UUID.fromString(bounty.split(":")[0])).getName() + " for $" + bounty.split(":")[1]);
                    }
                }
            }
        }
        else {
            double bounty;
            if (coolDown.get(player) != null) if (System.currentTimeMillis() - coolDown.get(player) < 5000) {
                player.sendMessage(ChatColor.RED+"Please wait five seconds before running the command again!");
                return true;
            }
            if (args.length == 1) {
                player.sendMessage(ChatColor.RED+"Please provide an amount for the bounty! e.g. 1000");
                return true;
            }
            if (!args[0].equals("*")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED+"That player is not online!");
                    return true;
                }
                if (target.equals(player)) {
                    player.sendMessage(ChatColor.RED+"You can't put a bounty on yourself!");
                    return true;
                }
                // SINGLE PLAYER BOUNTY
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
                double oldAmount = 0;
                for (String entry : playerBounties) if (entry.split(":")[0].equals(target.getUniqueId().toString())) {
                    oldAmount = Double.parseDouble(entry.split(":")[1]);
                    playerBounties.remove(entry);
                    message = message + "\nThe bounty total on "+target.getName()+" is $"+(bounty+oldAmount);
                    break;
                }
                playerBounties.add(target.getUniqueId()+":"+(bounty+oldAmount));
                Yesssirbox.getPlugin(Yesssirbox.class).getConfig().set("bounties", playerBounties);
                Yesssirbox.getPlugin(Yesssirbox.class).saveConfig();
                Yesssirbox.getPlugin(Yesssirbox.class).reloadConfig();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
                Yesssirbox.econ.withdrawPlayer(player, bounty);
                player.sendMessage(ChatColor.GREEN+"The bounty has been set and $"+bounty+" has been taken from your account.");
                coolDown.put(player, System.currentTimeMillis());
            }
            else {
                // ALL PLAYER BOUNTY
                double totalAmount;
                try {
                    bounty = Double.parseDouble(args[1]);
                    bounty = Math.floor(bounty*100)/100;
                    totalAmount = bounty*(Bukkit.getOnlinePlayers().size()-1);
                    if (bounty < 0) throw new NumberFormatException();
                }
                catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED+"Please enter a valid number! e.g. 1000");
                    return true;
                }
                if (Yesssirbox.econ.getBalance(player) < totalAmount) {
                    player.sendMessage(ChatColor.RED+"You cannot afford that bounty!");
                    return true;
                }
                List<String> playerBounties = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("bounties");
                String message = "&c&lyesssirbox &8&l>> &a"+player.getName()+" has just set a bounty of $"+bounty+" on EVERYONE ONLINE. It cost them $"+totalAmount+". What an idiot.";
                double oldAmount = 0;
                for (Player target : Bukkit.getOnlinePlayers()) {
                    for (String entry : playerBounties) if (entry.split(":")[0].equals(target.getUniqueId().toString())) {
                        oldAmount = Double.parseDouble(entry.split(":")[1]);
                        playerBounties.remove(entry);
                        break;
                    }
                    playerBounties.add(target.getUniqueId()+":"+(bounty+oldAmount));
                }
                Yesssirbox.getPlugin(Yesssirbox.class).getConfig().set("bounties", playerBounties);
                Yesssirbox.getPlugin(Yesssirbox.class).saveConfig();
                Yesssirbox.getPlugin(Yesssirbox.class).reloadConfig();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
                Yesssirbox.econ.withdrawPlayer(player, totalAmount);
                player.sendMessage(ChatColor.GREEN+"The bounties has been set and $"+totalAmount+" has been taken from your account. You're insane.");
                coolDown.put(player, System.currentTimeMillis());
            }
        }
        return true;
    }
}
