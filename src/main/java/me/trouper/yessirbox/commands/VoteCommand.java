package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

@CommandRegistry(value="vote",printStackTrace = true)
public class VoteCommand implements CustomCommand, Global {

    @Override
    public void dispatchCommand(CommandSender cs, Args args) {
        List<String> voteLinks = YessirBox.config.voteLinks;
        if (voteLinks.isEmpty()) {
            YessirBox.config.voteLinks.add("https://example.com/vote-for-yessirbox");
            YessirBox.config.save();
        }
        info(cs,color(YessirBox.config.prefix + "There are (&a" + voteLinks.size() + "&7) vote links (Clickable):"));
        for (String link : voteLinks) {
            cs.sendMessage(Component
                    .text(color("&8- &b" + link))
                    .clickEvent(ClickEvent.openUrl(link)));
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder completionBuilder) {

    }
}
