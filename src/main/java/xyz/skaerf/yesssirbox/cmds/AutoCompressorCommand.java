package xyz.skaerf.yesssirbox.cmds;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AutoCompressorCommand implements CommandExecutor {

    public static String autocomp = "§7Autocompressor";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("Please only Use the Command as a Player");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("syesssirbox.admin")) {
            player.sendMessage(Component.text(ChatColor.RED+"You do not have permission to do this!"));
            return true;
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            sender.sendMessage("You have to be holding an item!");
        }
        else {
            if (player.getInventory().getItemInMainHand().getLore() != null) {
                player.getInventory().getItemInMainHand().getLore().add(autocomp);
                player.sendMessage(ChatColor.GREEN + "Autocompressor has been put on your Item.");
            }
            else {
                List<String> lore = new ArrayList<>();
                lore.add(autocomp);
                player.getInventory().getItemInMainHand().setLore(lore);
                player.sendMessage(ChatColor.GREEN + "Autocompressor has been put on your Item.");
            }
        }
        return true;
    }
}
