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

import java.util.HashMap;
import java.util.UUID;

@CommandName("bounty")
public class BountyCommand implements Command {

    private final HashMap<Player, Long> coolDown = new HashMap<>();

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player player)) {
            error(sender, "no");
            return;
        }

        var playerBounties = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("bounties");
        var plugin = Yesssirbox.getPlugin(Yesssirbox.class);

        if (args.getSize() == 0) {
            if (playerBounties.isEmpty()) {
                info(sender, "&aThere are no bounties open at the moment! Check back later, or create your own with &l/bounty <player> <amount> &a!");
                return;
            }

            info(sender, "&aShowing bounties for online players only.");
            for (String bounty : playerBounties) {
                UUID id = UUID.fromString(bounty.split(":")[0]);
                Player target = Bukkit.getPlayer(id);
                String bal = bounty.split(":")[1];
                if (target != null && !Yesssirbox.isVanished(target)) {
                    info(sender, "&6Bounty on %s for $%s".formatted(Bukkit.getOfflinePlayer(id).getName(), bal));
                }
            }
        }
        else {
            double bounty = 0.0;
            if (coolDown.get(player) != null) if (System.currentTimeMillis() - coolDown.get(player) < 5000) {
                error(sender, "Please wait five seconds before running the command again!");
            }
            if (args.getSize() == 1) {
                error(sender, "Please provide an amount for the bounty! e.g. 1000");
            }

            if (!args.match(0, "*")) {
                Player target = Bukkit.getPlayer(args.get(0).stringValue());
                if (target == null) {
                    error(sender, "That player is not online!");
                }
                if (target.equals(player)) {
                    error(sender, "You can't put a bounty on yourself!");
                }

                // SINGLE PLAYER BOUNTY
                try {
                    bounty = Math.floor(args.get(1).doubleValue()*100)/100;
                    if (bounty < 0) throw new NumberFormatException();
                }
                catch (NumberFormatException e) {
                    error(sender, "Please enter a valid number! e.g. 1000");
                }

                if (Yesssirbox.econ.getBalance(player) < bounty) {
                    player.sendMessage(ChatColor.RED+"You cannot afford that bounty!");
                }

                String message = "&c&lyesssirbox &8&l>> &a%s has just set a bounty of $%s on %s!".formatted(player.getName(), bounty, target.getName());
                double oldAmount = 0;
                for (String entry : playerBounties) if (entry.split(":")[0].equals(target.getUniqueId().toString())) {
                    oldAmount = Double.parseDouble(entry.split(":")[1]);
                    playerBounties.remove(entry);
                    message = message + "\nThe bounty total on "+target.getName()+" is $"+(bounty+oldAmount);
                    break;
                }
                playerBounties.add(target.getUniqueId()+":"+(bounty+oldAmount));
                plugin.getConfig().set("bounties", playerBounties);
                plugin.saveConfig();
                plugin.reloadConfig();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    info(online, message);
                }
                Yesssirbox.econ.withdrawPlayer(player, bounty);
                player.sendMessage(ChatColor.GREEN+"The bounty has been set and $"+bounty+" has been taken from your account.");
                coolDown.put(player, System.currentTimeMillis());
            }
            else {
                // ALL PLAYER BOUNTY
                double totalAmount;
                try {
                    bounty = Math.floor(args.get(1).doubleValue()*100)/100;
                    totalAmount = bounty*(Bukkit.getOnlinePlayers().size()-1);
                    if (bounty < 0) throw new NumberFormatException();
                }
                catch (NumberFormatException e) {
                    error(sender, "Please enter a valid number! e.g. 1000");
                    return;
                }
                if (Yesssirbox.econ.getBalance(player) < totalAmount) {
                    error(sender, "You cannot afford that bounty!");
                    return;
                }

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
                plugin.getConfig().set("bounties", playerBounties);
                plugin.saveConfig();
                plugin.reloadConfig();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    info(online, message);
                }
                Yesssirbox.econ.withdrawPlayer(player, totalAmount);
                player.sendMessage(ChatColor.GREEN+"The bounties has been set and $"+totalAmount+" has been taken from your account. You're insane.");
                coolDown.put(player, System.currentTimeMillis());
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
