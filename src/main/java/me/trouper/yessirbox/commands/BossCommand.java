package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.bosses.witherskeleton.WSBoss;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;

@CommandRegistry(value="boss", playersOnly = true,permission=@Permission("op"))
public class BossCommand implements CustomCommand, Global {
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        switch (args.get(1).toString()) {
            case "spawn" -> {
                new WSBoss().spawn(WitherSkeleton.class,p.getLocation());
            }
            case "removebars" -> {
                var bars = p.activeBossBars();
                bars.forEach(p::hideBossBar);
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("spawn"));
        b.then(b.arg("removebars"));
    }
}
