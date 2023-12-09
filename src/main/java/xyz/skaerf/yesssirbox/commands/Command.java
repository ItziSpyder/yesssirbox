package xyz.skaerf.yesssirbox.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.skaerf.yesssirbox.Global;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;
import xyz.skaerf.yesssirbox.commands.completions.CompletionNode;

import java.util.ArrayList;
import java.util.List;

public interface Command extends TabExecutor, Global {

    void dispatchCommand(CommandSender sender, Args args);

    void dispatchCompletions(CompletionBuilder b);

    default void registerTo(JavaPlugin plugin) {
        String name = this.getClass().getAnnotation(CommandName.class).value();
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setTabCompleter(this);
    }

    @Override
    default boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            dispatchCommand(sender, new Args(args));
        }
        catch (Exception ex) {
            except(sender, ex);
        }
        return true;
    }

    @Override
    default List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, String[] args) {
        try {
            CompletionBuilder b = new CompletionBuilder(label);
            dispatchCompletions(b);
            CompletionNode node = b.getRootNode();

            if (args.length == 0) {
                return node.getOptions();
            }
            for (int i = 0; i < args.length - 1; i++) {
                node = node.next(args[i]);
            }

            List<String> a = new ArrayList<>(node.getOptions());
            a.removeIf(s -> !s.toLowerCase().contains(args[args.length - 1].toLowerCase()));
            return a;
        }
        catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
