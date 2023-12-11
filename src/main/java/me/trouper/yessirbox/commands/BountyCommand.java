package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.data.Bounty;
import me.trouper.yessirbox.data.BountyStorage;
import me.trouper.yessirbox.utils.TimeStamp;
import me.trouper.yessirbox.utils.Utils;
import org.bukkit.Bukkit;
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
        switch (args.first().stringValue()) {
            case "set" -> {
                if (args.get(2).longValue() < YessirBox.config.bounties.minWorth) {
                    info(p, color(YessirBox.config.prefix + "&cThe minimum amount for a bounty is " + YessirBox.config.bounties.minWorth + "!"));
                    return;
                }
                if (Math.floor(args.get(3).doubleValue()) != args.get(3).doubleValue() || args.get(3).doubleValue() < YessirBox.config.bounties.minTime) {
                    info(p, color(YessirBox.config.prefix + "&cYou must provide a whole number for the duration of your bounty!"));
                    return;
                }
                if (Objects.equals(args.get(1).stringValue(), p.getName())) {
                    info(p, color(YessirBox.config.prefix + "&cYou cannot set a bounty on yourself!"));
                    return;
                }
                if (YessirBox.econ.getBalance(p) <= args.get(2).longValue()) {
                    info(p, color(YessirBox.config.prefix + "&cYou cannot afford that bounty!"));
                    return;
                }
                if (!YessirBox.config.bounties.allowDuplicates) {
                    for (Bounty bounty : YessirBox.bounties.storage) {
                        if (bounty.target() == Bukkit.getOfflinePlayer(args.get(1).stringValue()).getUniqueId()) {
                            info(p,color(YessirBox.config.prefix + "&cDuplicating YessirBox.bounties.storage is not allowed! " + Bukkit.getOfflinePlayer(bounty.setter()).getName() + " has already set a bounty on " + Bukkit.getOfflinePlayer(bounty.target()).getName()));
                            return;
                        }
                    }
                }
                TimeStamp now = TimeStamp.now();
                Bounty b = new Bounty(p.getUniqueId(),
                        Bukkit.getOfflinePlayer(args.get(1).stringValue()).getUniqueId(),
                        args.get(2).intValue(),
                        now,
                        args.get(3).longValue());
                YessirBox.econ.withdrawPlayer(p,b.amount());
                YessirBox.bounties.storage.add(b);
                info(p,color(YessirBox.config.prefix + "Set a bounty on &e" + Bukkit.getOfflinePlayer(b.target()).getName() + "&7 worth &2$" + b.amount() + "&7, it will expire in &a" + b.duration() + " &7hours."));
                YessirBox.bounties.save();
            }
            case "cancel" -> {
                for (Bounty bounty : YessirBox.bounties.storage) {
                    if (bounty.target() == Bukkit.getOfflinePlayer(args.get(1).stringValue()).getUniqueId()) {
                        if (bounty.setter() == p.getUniqueId()) {
                            YessirBox.bounties.storage.remove(bounty);
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
                for (Bounty bounty : YessirBox.bounties.storage) {
                    if (!(bounty.target() == Bukkit.getOfflinePlayer(args.get(1).stringValue()).getUniqueId())) continue;
                    info = bounty;
                    break;
                }
                if (info.amount() == 0) {
                    info(p,color(YessirBox.config.prefix + "No bounty exists on &e" + args.get(1)));
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
                .then(b.arg(activeBountiedNames())));
    }

    public static List<String> activeBountiedNames() {
        List<String> out = new ArrayList<>();
        YessirBox.log.info("Attempting to list active bounties...");
        for (Bounty bounty : YessirBox.bounties.storage) {
            YessirBox.log.info("Looping through bounty, " + bounty.target());
            if (isExpired(bounty)) {
                YessirBox.log.info("A bounty on " + bounty.target() + " has been found to be expired. Removing.");
                YessirBox.bounties.storage.remove(bounty);
            } else {
                out.add(Bukkit.getOfflinePlayer(bounty.target()).getName());
                YessirBox.log.info("A bounty on " + bounty.target() + " has been found and added.");
            }
        }
        YessirBox.log.info("No bounties has been found.");
        if (out.isEmpty()) out.add("No Bounties Found");
        return out;
    }

    public static boolean isExpired(Bounty b) {
        long lasted = b.created().secondsBetween(TimeStamp.now());
        long max = b.duration() * 3600;
        YessirBox.log.info("Checking bounty validity: " + b.target() + " It has been around for " + lasted + " seconds, its max time is " + max + " seconds.");
        return lasted >= max;
    }
}
