package xyz.skaerf.yesssirbox.cmds;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import xyz.skaerf.yesssirbox.Compressable;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.HashMap;
import java.util.List;

public class CompressCommand implements CommandExecutor {

    HashMap<ItemStack, ItemStack> compressTo = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String [] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("syesssirbox.compress")) {
            player.sendMessage(Component.text(ChatColor.RED+"You don't have permission to use compression! You can buy it on the store though. /buy"));
            return true;
        }
        compress(player);
        player.sendMessage(ChatColor.GREEN + "Your inventory has been compressed.");
        return false;
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
