package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.data.guis.StoreGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value="store",printStackTrace = true,playersOnly = true)
public class StoreCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;
        info(p, color(YessirBox.config.prefix + "Opening Store"));
        p.openInventory(StoreGUI.home.getInventory());
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
