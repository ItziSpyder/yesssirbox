package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

import java.util.HashMap;

@CommandName("autocompress")
public class AutoCompressCommand implements Command {

    public static HashMap<Player, Boolean> autoc = new HashMap<>();

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player player)){
            error(sender, "Please only Use the Command as a Player");
            return;
        }
        if (!player.hasPermission("syesssirbox.ac")) {
            error(sender, "Sidiju doesn't think you have the permission for that. ;) you can (/buy) it if you want.");
            return;
        }

        if (autoc.containsKey(player)){
            autoc.remove(player);
            info(sender, "&cTurned the AutoCompressor off.");
        }
        else {
            autoc.put(player, true);
            info(sender, "&aTurned the AutoCompressor on.");
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
