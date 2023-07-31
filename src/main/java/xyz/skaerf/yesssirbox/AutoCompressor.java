package xyz.skaerf.yesssirbox;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class AutoCompressor implements CommandExecutor {

    static HashMap<ItemStack, ItemStack> compressTo = new HashMap<>();

    public static void autoCompress(BlockBreakEvent event) {
        Player player = event.getPlayer();

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
            // if stack is enough and of the right type, check name for 'compressed'. if already says it, just add a multiplier
            if (compressTo.get(stack) != null) {
                player.getInventory().remove(stack);
                player.getInventory().addItem(compressTo.get(stack));
                /* i know this is not going to be sufficient but the idea is if this doesnt do what they want then they
                * can pay for the autocompresssor! i had done less than id thought lol sorry
                */
            }
        }
        player.sendMessage(ChatColor.GREEN+"Every possible compression was made!");
        return true;
    }

    @SuppressWarnings("unchecked")
    public static void setCompressorItems(FileConfiguration config) {
        List<ItemStack> from = (List<ItemStack>) config.getList("compressFrom");
        List<ItemStack> to = (List<ItemStack>) config.getList("compressTo");
        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().warning("Could not grab compressor items - one or both lists are null or empty");
            return;
        }
        for (int i = 0; i < from.size(); i++) {
            compressTo.put(from.get(i), to.get(i));
        }
    }
}
