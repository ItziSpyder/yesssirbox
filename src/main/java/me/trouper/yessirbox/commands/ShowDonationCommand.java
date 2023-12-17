package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.itzispyder.pdk.utils.ArrayUtils;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import me.trouper.yessirbox.utils.ImageUtils;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
@CommandRegistry(value="showdonation", permission=@Permission(value="yessirbox.showdonation",message = "You must donate before you can show a donation!"))
public class ShowDonationCommand implements CustomCommand, Global {
    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        String name = args.get(0).toString();
        String amount = args.get(1).toString();
        String price = args.get(2).toString();
        String item = args.getAll(3).toString();
        OfflinePlayer p = Bukkit.getOfflinePlayer(name);
        List<String> imageLines = ImageUtils.imageToList("https://crafatar.com/avatars/" + p.getUniqueId() + "?size=8&overlay");
        imageLines.set(2,imageLines.get(2) + " §b§k... §7DONATION ALERT §b§k...");
        imageLines.set(3,imageLines.get(3) + " §7Thank you, §b§n" + name + "§7 for purchasing,");
        imageLines.set(4,imageLines.get(4) + " §7" + amount + "x " + color(item) + "§7 for §2$" + price);
        imageLines.set(5,imageLines.get(5) + " §7Every donation helps!");
        imageLines.set(7,imageLines.get(7) + " §8§m==========================");
        imageLines.set(0,imageLines.get(0) + " §8§m==========================");
        Bukkit.broadcastMessage("\n");
        Bukkit.broadcastMessage(String.join("\n", imageLines));
        Bukkit.broadcastMessage("\n");
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("user").then(b.arg("amount").then(b.arg("price").then(b.arg("product")))));
    }
}
