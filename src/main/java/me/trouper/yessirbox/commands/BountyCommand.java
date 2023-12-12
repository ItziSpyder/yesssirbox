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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CommandRegistry(value="bounty",usage="/bounty <set|cancel|check> <player> [<amount>] [<duration (hours)>]", printStackTrace = true, playersOnly = true)
public class BountyCommand implements CustomCommand, Global {

    @Override
    public void dispatchCommand(CommandSender commandSender, Args args) {
        Player p = (Player) commandSender;
        double duration = args.get(3).doubleValue();
        double worth = args.get(2).doubleValue();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args.get(1).stringValue());
        switch (args.first().stringValue()) {
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
                if (!YessirBox.config.bounties.allowDuplicates) {
                    for (Bounty bounty : YessirBox.bounties.getBounties()) {
                        if (bounty.target() == target.getUniqueId()) {
                            info(p,color(YessirBox.config.prefix + "&cDuplicating bounties is not allowed! " + Bukkit.getOfflinePlayer(bounty.setter()).getName() + " has already set a bounty on " + Bukkit.getOfflinePlayer(bounty.target()).getName()));
                            return;
                        }
                    }
                }
                TimeStamp now = TimeStamp.now();
                Bounty b = new Bounty(p.getUniqueId(),
                        target.getUniqueId(),
                        args.get(2).intValue(),
                        now,
                        args.get(3).longValue());
                YessirBox.econ.withdrawPlayer(p,b.amount());
                YessirBox.bounties.addBounty(b);
                YessirBox.bounties.save();
                info(p,color(YessirBox.config.prefix + "Set a bounty on &e" + Bukkit.getOfflinePlayer(b.target()).getName() + "&7 worth &2$" + b.amount() + "&7, it will expire in &a" + b.duration() + " &7hours."));
            }
            case "cancel" -> {
                for (Bounty bounty : YessirBox.bounties.getBounties()) {
                    if (bounty.target() == target.getUniqueId()) {
                        if (bounty.setter() == p.getUniqueId()) {
                            YessirBox.bounties.removeBounty(bounty);
                            info(p,color(YessirBox.config.prefix + "Removed your bounty from &e" + args.get(1).stringValue()));
                        } else {
                            info(p,color(YessirBox.config.prefix + "You do not own the bounty on &e" + args.get(1).stringValue()));
                        }
                        return;
                    }
                }
                info(p,color(YessirBox.config.prefix + "Could not find a bounty on &e" + args.get(1).stringValue()));
                YessirBox.bounties.save();
            }
            case "check" -> {
                Bounty info = new Bounty(p.getUniqueId(),p.getUniqueId(),0,TimeStamp.now(),0);
                for (Bounty bounty : YessirBox.bounties.getBounties()) {
                    if (!(bounty.target() == Bukkit.getOfflinePlayer(args.get(1).stringValue()).getUniqueId())) continue;
                    info = bounty;
                    break;
                }
                if (info.amount() == 0) {
                    info(p,color(YessirBox.config.prefix + "No bounty exists on &e" + args.get(1).stringValue()));
                    return;
                }
                long lasted = info.created().secondsBetween(TimeStamp.now());
                long max = info.duration() * 3600;
                info(p,color(YessirBox.config.prefix + "Found a bounty on &e" + Bukkit.getOfflinePlayer(args.get(1).stringValue()).getName() +
                        "&7 worth &2$" + info.amount() +
                        "&7, it expires in &c" + (Math.floor((double) (max - lasted) / 60) ) + "&7 minutes."));
                YessirBox.bounties.save();
            }
            default -> {
                info(p, color(YessirBox.config.prefix + "&cIncorrect usage! /bounty <set|cancel|check> <player> [<amount>] [<duration (hours)>]"));
            }
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
    }
}
