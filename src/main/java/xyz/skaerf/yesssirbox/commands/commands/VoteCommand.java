package xyz.skaerf.yesssirbox.commands.commands;

import org.bukkit.command.CommandSender;
import xyz.skaerf.yesssirbox.Yesssirbox;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

@CommandName("vote")
public class VoteCommand implements Command {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        var list = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("voteMessages");
        if (list.isEmpty()) {
            error(sender, "Please ask a staff member for the Vote-Links!");
            return;
        }

        for (String str : list) {
            info(sender, str);
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
