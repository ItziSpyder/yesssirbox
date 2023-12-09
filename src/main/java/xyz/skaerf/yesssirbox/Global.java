package xyz.skaerf.yesssirbox;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public interface Global {

    default <T extends JavaPlugin> T getPlugin(Class<T> pluginClass) {
        return JavaPlugin.getPlugin(pluginClass);
    }

    default Yesssirbox getThisPlugin() {
        return getPlugin(Yesssirbox.class);
    }

    default void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(getThisPlugin(), task);
    }

    default void info(CommandSender sender, String msg) {
        sender.sendMessage(msg.replace('&', '§'));
    }

    default void error(CommandSender sender, String msg) {
        info(sender, "§c" + msg);
    }

    default void except(CommandSender sender, Exception ex) {
        String type = ex.getClass().getSimpleName();
        String msg = ex.getMessage();
        error(sender, """
                    §cError:
                    §7-§c Type: §7%s
                    §7-§c Message: §7%s
                    """.formatted(type, msg));
    }
}
