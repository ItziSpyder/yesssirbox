package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.List;

public class CompressToCommand implements CommandExecutor {

    @Override
    @SuppressWarnings("unchecked") // complaining about unchecked casts from generic type List to List<ItemStack> type
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Console cannot set values for compressing - only a player can as you must be holding the ItemStacks.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("yesssirbox.admin")) {
            player.sendMessage(ChatColor.RED+"You don't have permission to do this.");
            return true;
        }
        ItemStack compressFrom = player.getInventory().getItem(0);
        ItemStack compressTo = player.getInventory().getItem(1);
        if (compressFrom == null || compressTo == null || compressFrom.getType().equals(Material.AIR) || compressTo.getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.RED+"Please make sure that you are holding the item you want to compress from in your first slot" +
                    " and the item to compress to in your second slot e.g. 64 oak logs in slot 1 and 1 Compressed Log in slot 2.");
            return true;
        }
        List<ItemStack> compressFromList = (List<ItemStack>) Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getList("compressFrom");
        List<ItemStack> compressToList = (List<ItemStack>) Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getList("compressTo");

        compressFromList.add(compressFrom);
        compressToList.add(compressTo);
        if (compressFromList.size() != compressToList.size()) {
            player.sendMessage(ChatColor.RED+"There has been a critical error. Please report this to skaerf:\nListLengthMismatch");
            return true;
        }
        Yesssirbox.getPlugin(Yesssirbox.class).getConfig().set("compressFrom", compressFromList);
        Yesssirbox.getPlugin(Yesssirbox.class).getConfig().set("compressTo", compressToList);
        Yesssirbox.getPlugin(Yesssirbox.class).saveConfig();
        Yesssirbox.getPlugin(Yesssirbox.class).reloadConfig();
        player.sendMessage(ChatColor.GREEN+"Successfully saved new compress translation to config.");
        return true;
    }
}
