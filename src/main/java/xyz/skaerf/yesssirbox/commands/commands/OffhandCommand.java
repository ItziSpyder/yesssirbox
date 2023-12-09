package xyz.skaerf.yesssirbox.commands.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

@CommandName("offhand")
public class OffhandCommand implements Command {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You have no hands, never mind an offhand mate."));
            return;
        }

        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.RED+"You have nothing in your main hand - please hold something and run the command again.");
            return;
        }
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();
        player.getInventory().setItemInOffHand(main);
        player.getInventory().setItemInMainHand(off);
        player.sendMessage("Items have been switched.");
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
