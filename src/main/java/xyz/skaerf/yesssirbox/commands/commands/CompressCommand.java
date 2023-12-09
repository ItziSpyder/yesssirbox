package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.skaerf.yesssirbox.Yesssirbox;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;
import xyz.skaerf.yesssirbox.server.Compressable;

import java.util.List;

@CommandName("compress")
public class CompressCommand implements Command {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player player = (Player) sender;
        if (!player.hasPermission("syesssirbox.compress")) {
            error(sender, "You don't have permission to use compression! You can buy it on the store though. /buy");
            return;
        }
        compress(player);
        info(sender, "&aYour inventory has been compressed.");
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }

    public void compress(Player player) {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack != null && !stack.getType().equals(Material.AIR)) { // && isCompressable
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // the reason this is a list is to ensure that if the entire stack is not compressed, it is not all taken
                        List<ItemStack> replacement = Compressable.compress(stack);
                        System.out.println(replacement);
                        if (replacement != null) {
                            player.getInventory().remove(replacement.get(0));
                            player.getInventory().addItem(replacement.get(1));
                        }
                    }
                }.runTask(Yesssirbox.getPlugin(Yesssirbox.class));
            }
        }
    }
}
