package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.events.AutoCompressorEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandRegistry(value="autocompressor",permission=@Permission(value="yessirbox.toggleautocompress", message="You do not have permission to use compress, purchase it with /buy!"),usage="/autocompressor")
public class AutoCompressorCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player p)) {
            return;
        }
        UUID u = p.getUniqueId();
        if (!AutoCompressorEvent.map.containsKey(u)) {
            AutoCompressorEvent.map.put(u,true);
        } else {
            AutoCompressorEvent.map.put(u,!AutoCompressorEvent.map.get(u));
        }
        info(p, color(YessirBox.config.prefix + "Toggled the AutoCompressor " + (AutoCompressorEvent.map.get(u) ? "&aon&7." : "&coff&7.")));
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
