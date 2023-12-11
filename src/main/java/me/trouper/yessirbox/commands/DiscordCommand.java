package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
@CommandRegistry(value="discord")
public class DiscordCommand implements CustomCommand, Global {

    @Override
    public void dispatchCommand(CommandSender commandSender, Args args) {
        commandSender.sendMessage(Component.text(color(YessirBox.config.prefix + "Click here to join the discord! &b" + YessirBox.config.discordInvite))
                .hoverEvent(HoverEvent.showText(Component.text(color("&a&lClick Me!"))))
                .clickEvent(ClickEvent.openUrl(YessirBox.config.discordInvite)));
    }

    @Override
    public void dispatchCompletions(CompletionBuilder completionBuilder) {

    }
}
