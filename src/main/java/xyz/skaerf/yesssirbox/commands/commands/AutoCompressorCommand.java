package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

import java.util.ArrayList;

@CommandName("autocompressor")
public class AutoCompressorCommand implements Command {

    public static String autocomp = "§7Autocompressor";

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player player)){
            error(sender, "Please only Use the Command as a Player");
            return;
        }
        if (!player.hasPermission("syesssirbox.admin")) {
            error(sender, "You do not have permission to do this!");
            return;
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            error(sender, "You have to be holding an item!");
            return;
        }

        var item = player.getInventory().getItemInMainHand();
        var lore = item.getLore() != null ? item.getLore() : new ArrayList();

        lore.add(autocomp);
        item.setLore(lore);
        info(sender, "&aAutocompressor has been put on your Item.");
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
