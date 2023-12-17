package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.data.Bounty;
import me.trouper.yessirbox.utils.TimeStamp;
import me.trouper.yessirbox.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value="bounty",usage="/bounty <set|cancel|check> <player> [<amount>] [<duration (hours)>]", printStackTrace = true, playersOnly = true)
public class BountyCommand implements CustomCommand, Global {

    @Override
    public void dispatchCommand(CommandSender commandSender, Args args) {
        Player p = (Player) commandSender;
        double duration = args.get(3).toDouble();
        double worth = args.get(2).toDouble();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(1).toString());
        switch (args.first().toString()) {
            case "list" -> {
                if (YessirBox.bounties.getActiveBounties().isPresent()) {
                    info(p,color("\n" + YessirBox.config.prefix + "There are &a" + YessirBox.bounties.getBounties().size() + "&7 bounties. &8(&a✔ &7= target online&8)"));
                    for (Bounty b : YessirBox.bounties.getActiveBounties().get()) {
                        long lasted = b.created().secondsBetween(TimeStamp.now());
                        long max = b.duration() * 3600;
                        double expires = (Math.floor((double) (max - lasted) / 60));
                        OfflinePlayer t = Bukkit.getOfflinePlayer(b.target());
                        OfflinePlayer s = Bukkit.getOfflinePlayer(b.setter());
                        boolean tOn = t.isConnected();
                        info(p,"&8&m|----------------------------------------------|");
                        info(p, color((tOn ? "&b - &a✔ " : "&b - &c✘ ") + "&7Target: &c" + t.getName() + "&7 Setter: &b" + s.getName() + "&7 Worth: &2$" + b.amount() + "\n&7 Expires: &6" + expires + "&7 minutes."));
                    }
                    return;
                }
                info(p,color(YessirBox.config.prefix+ "There are currently no bounties. Set one on a player with /bounty set"));
            }
            case "set" -> {
                if (worth < YessirBox.config.bounties.minWorth ) {
                    info(p, color(YessirBox.config.prefix + "&cYou must provide a value between " + YessirBox.config.bounties.minWorth + " and " + YessirBox.config.bounties.maxWorth + " for the worth!"));
                    return;
                }
                if (Math.floor(duration) != duration || duration < YessirBox.config.bounties.minTime || duration > YessirBox.config.bounties.maxTime) {
                    info(p, color(YessirBox.config.prefix + "&cYou must provide a value between " + YessirBox.config.bounties.minTime + " and " + YessirBox.config.bounties.maxTime + " for the duration of your bounty!"));
                    return;
                }
                if (target == p) {
                    info(p, color(YessirBox.config.prefix + "&cYou cannot set a bounty on yourself!"));
                    return;
                }
                if (YessirBox.econ.getBalance(p) <= worth) {
                    info(p, color(YessirBox.config.prefix + "&cYou cannot afford that bounty!"));
                    return;
                }
                if (!YessirBox.config.bounties.allowDuplicates && YessirBox.bounties.bountyExists(target.getUniqueId())) {
                    var exists = YessirBox.bounties.findBounty(target.getUniqueId());
                    info(p,color(YessirBox.config.prefix + "&cDuplicating bounties is not allowed! " + Bukkit.getOfflinePlayer(exists.get().setter()).getName() + " has already set a bounty on " + target.getName()));
                    return;
                }
                TimeStamp now = TimeStamp.now();
                Bounty b = new Bounty(p.getUniqueId(),
                        target.getUniqueId(),
                        args.get(2).toInt(),
                        now,
                        args.get(3).toLong());
                YessirBox.econ.withdrawPlayer(p,b.amount());
                YessirBox.bounties.addBounty(b);
                YessirBox.bounties.save();
                info(p,color(YessirBox.config.prefix + "Set a bounty on &e" + Bukkit.getOfflinePlayer(b.target()).getName() + "&7 worth &2$" + b.amount() + "&7, it will expire in &a" + b.duration() + " &7hours."));
            }
            case "cancel" -> {
                var b = YessirBox.bounties.findBounty(target.getUniqueId());
                if (b.isPresent() && b.get().setter().equals(p.getUniqueId()) ) {
                    YessirBox.bounties.removeBounty(b.get());
                    info(p,color(YessirBox.config.prefix + "Removed your bounty from &e" + args.get(1).toString()));
                    return;
                } else if (!b.isPresent()) {
                    info(p,color(YessirBox.config.prefix + "Could not find a bounty on &e" + args.get(1).toString()));
                    YessirBox.bounties.save();
                    return;
                }
                info(p,color(YessirBox.config.prefix + "You do not own the bounty on &e" + args.get(1).toString()));
            }
            case "check" -> {
                var b = YessirBox.bounties.findBounty(target.getUniqueId());
                if (b.isPresent()) {
                    long lasted = b.get().created().secondsBetween(TimeStamp.now());
                    long max = b.get().duration() * 3600;
                    double expires = (Math.floor((double) (max - lasted) / 60));
                    info(p, color(YessirBox.config.prefix + "Found a bounty on &e" + Bukkit.getOfflinePlayer(args.get(1).toString()).getName() +
                            "&7 worth &2$" + b.get().amount() +
                            "&7, it expires in &c" + expires + "&7 minutes."));
                    YessirBox.bounties.save();
                }
                info(p, color(YessirBox.config.prefix + "No bounty exists on &e" + args.get(1).toString()));
            }
            default -> info(p, color(YessirBox.config.prefix + "&cIncorrect usage! /bounty <set|cancel|check> <player> [<amount>] [<duration (hours)>]"));
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("set")
                .then(b.arg(Utils.unVanishedPlayers())
                        .then(b.arg("<amount>")
                                .then(b.arg("[<hours>]")))));
        b.then(b.arg("cancel")
                .then(b.arg(Utils.unVanishedPlayers())));
        b.then(b.arg("check")
                .then(b.arg(YessirBox.bounties.getActiveBountyNames())));
        b.then(b.arg("list"));
    }
}
