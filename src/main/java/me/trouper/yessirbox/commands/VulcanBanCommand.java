package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value="vulcanban", permission=@Permission(value="ysb.vulcanban", message="You are not the anticheat!"),printStackTrace = true)
public class VulcanBanCommand implements CustomCommand, Global {
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Bukkit.broadcast(Component.text(color(YessirBox.config.prefix + "Vulcan AntiCheat has removed &c" + args.get(1) + "&7 from the game due to cheating. I'm always watching...")));
        Bukkit.getScheduler().runTask(YessirBox.getInstance(), e -> {
            banPlayer(Bukkit.getPlayer(args.get(1).toString()));
        });

    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg(Utils.unVanishedPlayers()));
    }

    public static void banPlayer(OfflinePlayer p) {
        YessirBox.banStorage.incrementCount(p);
        YessirBox.banStorage.save();
        if (YessirBox.banStorage.getStorage().containsKey(p.getUniqueId())) {
            if (YessirBox.banStorage.getStorage().get(p.getUniqueId()) <= 1) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 1h -s [AntiCheat]\n You have been automatically temp-banned for the use of unfair advantages! Appeal on discord.gg/jmtPkbWjD7");
            } else if (YessirBox.banStorage.getStorage().get(p.getUniqueId()) == 2 ) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 1d -s [AntiCheat]\n You have been automatically temp-banned for the use of unfair advantages! Appeal on discord.gg/jmtPkbWjD7");
            } else if (YessirBox.banStorage.getStorage().get(p.getUniqueId()) == 3 ) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 3d -s [AntiCheat]\n You have been automatically temp-banned for the use of unfair advantages! Appeal on discord.gg/jmtPkbWjD7");
            } else if (YessirBox.banStorage.getStorage().get(p.getUniqueId()) == 4 ) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 7d -s [AntiCheat]\n You have been automatically temp-banned for the use of unfair advantages! Appeal on discord.gg/jmtPkbWjD7");
            } else if (YessirBox.banStorage.getStorage().get(p.getUniqueId()) == 5 ) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 30d -s [AntiCheat]\n You have been automatically temp-banned for the use of unfair advantages! Appeal on discord.gg/jmtPkbWjD7");
            } else if (YessirBox.banStorage.getStorage().get(p.getUniqueId()) >= 10 ) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " -s [AntiCheat]\n You have been automatically banned for the repeated use of unfair advantages! Appeal on discord.gg/jmtPkbWjD7");
            }
        }

    }
}
