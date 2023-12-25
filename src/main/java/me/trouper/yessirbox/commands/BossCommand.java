package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;

@CommandRegistry(value="boss", playersOnly = true,permission=@Permission("op"))
public class BossCommand implements CustomCommand, Global {
    public static Component bossName = Component.text(instance.color("&4&lWither Skeleton"));
    public static BossBar b = BossBar.bossBar(bossName,1, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_6);
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        switch (args.get(1).toString()) {
            case "spawn" -> {
                WitherSkeleton skeleton = p.getWorld().spawn(p.getLocation(), WitherSkeleton.class);
                skeleton.customName(bossName);
                skeleton.setAI(false);
                skeleton.registerAttribute(Attribute.GENERIC_MAX_HEALTH);
                skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
                skeleton.setHealth(100);
                b.addFlag(BossBar.Flag.CREATE_WORLD_FOG);
                p.showBossBar(b);
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
