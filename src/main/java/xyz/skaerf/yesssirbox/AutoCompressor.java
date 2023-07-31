package xyz.skaerf.yesssirbox;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoCompressor implements CommandExecutor {


    public static void autoCompress(BlockBreakEvent event) {
        
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("console doesnt have an inventory mate, you can't compress");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("yesssirbox.compress")) {
            player.sendMessage(ChatColor.RED+"You don't have permission to do this!");
            return true;
        }
        for (ItemStack stack : player.getInventory()) {
            // if stack is enough and of the right type, enchant sharp 1 and check name for 'compressed'. if already says it, just add a multiplier
        }
        return true;
    }
}
