package xyz.skaerf.yesssirbox;

import org.bukkit.command.CommandSender;

public interface Global {

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
