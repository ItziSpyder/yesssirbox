package xyz.skaerf.yesssirbox.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.List;

public class YesssirboxCommand implements CommandExecutor {

    JavaPlugin pl = Yesssirbox.getPlugin(Yesssirbox.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) if (!sender.hasPermission("yesssirbox.admin")) {
            sender.sendMessage(ChatColor.RED+"You don't have permission to execute this command!");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN+"/yesssirbox <reload/addBlock/addCompressor> [value/pre]");
        }
        else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            pl.reloadConfig();
            ShopCommand.setItems(pl.getConfig());
            Yesssirbox.reloadAllConfigs();
            Yesssirbox.loadBlockedWords();
            Yesssirbox.loadCompressables();
            Yesssirbox.setDailies();
            sender.sendMessage(ChatColor.GREEN+"Config reloaded!");
        }
        else if (args[0].equalsIgnoreCase("addBlock") || args[0].equalsIgnoreCase("ab")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Console is unable to do this as you must be holding items.");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED+"Please put the value that you want the block to have! /yesssirbox addBlock [value]");
                return true;
            }
            List<String> blockValues =  pl.getConfig().getStringList("blockValues");
            blockValues.add(((Player) sender).getInventory().getItemInMainHand().getType()+":"+args[1]);
            pl.getConfig().set("blockValues", blockValues);
            pl.saveConfig();
            pl.reloadConfig();
            Yesssirbox.refreshBlockValues();
            sender.sendMessage(ChatColor.GREEN+((Player)sender).getInventory().getItemInMainHand().getType().toString()+" has been added to the config with a value of $"+args[1]+"!");
        }
        else if (args[0].equalsIgnoreCase("ac") || args[0].equalsIgnoreCase("addCompressor")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Console is unable to do this as you must be holding items.");
                return true;
            }
            Player player = (Player) sender;
            if (player.getInventory().getItem(0) == null || player.getInventory().getItem(1) == null) {
                player.sendMessage(ChatColor.RED+"Please put the item that you wish to compress FROM in your first hotbar slot, and the item you want to compress TO in your second hotbar slot.");
                return true;
            }
            if (args.length == 2 && args[1].equalsIgnoreCase("pre")) {
                player.sendMessage(ChatColor.GREEN+"Item is being saved as a pre-compression. This ignores meta.");
            }
            ItemStack compressFrom = player.getInventory().getItem(0);
            ItemStack compressTo = player.getInventory().getItem(1);
            Yesssirbox.addToCompressables(compressFrom, compressTo);
            Yesssirbox.saveCompressables();
            player.sendMessage(ChatColor.GREEN+"New compressables added.");
        }
        else {
            sender.sendMessage(ChatColor.RED+"That is not a valid argument.");
        }
        return true;
    }
}
