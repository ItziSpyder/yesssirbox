package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.skaerf.yesssirbox.Yesssirbox;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

import java.util.List;

@CommandName("yesssirbox")
public class YesssirboxCommand implements Command {

    private final JavaPlugin pl = Yesssirbox.getPlugin(Yesssirbox.class);

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (sender instanceof Player && !sender.hasPermission("yesssirbox.admin")) {
            error(sender, "You don't have permission to execute this command!");
            return;
        }
        if (args.getSize() == 0) {
            info(sender, "&a/yesssirbox <reload/addBlock/addCompressor> [value/pre]");
        }
        else if (args.match(0, "reload") || args.match(0, "rl")) {
            pl.reloadConfig();
            ShopCommand.setItems(pl.getConfig());
            Yesssirbox.reloadAllConfigs();
            Yesssirbox.loadBlockedWords();
            Yesssirbox.loadCompressables();
            Yesssirbox.setDailies();
            sender.sendMessage(ChatColor.GREEN+"Config reloaded!");
        }
        else if (args.match(0, "addblock") || args.match(0, "ab")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Console is unable to do this as you must be holding items.");
                return;
            }
            if (args.getSize() < 2) {
                sender.sendMessage(ChatColor.RED+"Please put the value that you want the block to have! /yesssirbox addBlock [value]");
                return;
            }
            List<String> blockValues =  pl.getConfig().getStringList("blockValues");
            blockValues.add(((Player) sender).getInventory().getItemInMainHand().getType()+":"+args.get(1).stringValue());
            pl.getConfig().set("blockValues", blockValues);
            pl.saveConfig();
            pl.reloadConfig();
            Yesssirbox.refreshBlockValues();
            sender.sendMessage(ChatColor.GREEN+((Player)sender).getInventory().getItemInMainHand().getType().toString()+" has been added to the config with a value of $"+args.get(1).stringValue()+"!");
        }
        else if (args.match(0, "addcompressor") || args.match(0, "ac")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Console is unable to do this as you must be holding items.");
                return;
            }
            if (player.getInventory().getItem(0) == null || player.getInventory().getItem(1) == null) {
                player.sendMessage(ChatColor.RED+"Please put the item that you wish to compress FROM in your first hotbar slot, and the item you want to compress TO in your second hotbar slot.");
                return;
            }
            if (args.getSize() == 2 && args.match(1, "pre")) {
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
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
