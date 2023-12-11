package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.system.CompressInstruction;
import me.trouper.yessirbox.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@CommandRegistry(value="ysb",permission=@Permission(value="YessirBox.admin",message="No Perm!"))
public class AdminCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player p)) {
            return;
        }
        switch (args.first().stringValue()) {
            case "reload" -> {
                YessirBox.reloadKonfig();
                info(p,color(YessirBox.config.prefix + "Reloaded the config."));
            }
            case "compressor" -> {
                ItemStack main = p.getInventory().getItemInMainHand();
                ItemStack off = p.getInventory().getItemInOffHand();
                if (!main.getType().isAir() && !off.getType().isAir()) {
                    switch (args.get(1).stringValue()) {
                        case "add" -> {
                            YessirBox.compressionRegistry.registerInstructions(new CompressInstruction(main, off));
                            YessirBox.compressionRegistry.save();
                            p.sendMessage(Component
                                    .text(color(YessirBox.config.prefix + "Added a new compressible item, Uncompressed: &b" + Utils.getItemName(main) + "&7. (Hover Expanded Item)"))
                                    .hoverEvent(main));
                            p.sendMessage(Component
                                    .text(color(YessirBox.config.prefix + "Compressed: &b" + Utils.getItemName(off) + "&7. (Hover Compressed Item)"))
                                    .hoverEvent(off));
                        }
                        case "remove" -> {
                            YessirBox.compressionRegistry.removeInstructions(new CompressInstruction(main, off));
                            YessirBox.compressionRegistry.save();
                            p.sendMessage(Component
                                    .text(color(YessirBox.config.prefix + "Removed a compressible item, Uncompressed: &b" + Utils.getItemName(main) + "&7. (Hover Expanded Item)"))
                                    .hoverEvent(main));
                            p.sendMessage(Component
                                    .text(color(YessirBox.config.prefix + "Compressed: &b" + Utils.getItemName(off) + "&7. (Hover Compressed Item)"))
                                    .hoverEvent(off));
                        }
                    }
                }
            }
            case "autocompressor" -> {

                switch (args.get(1).stringValue()) {
                    case "apply" -> handleApplyAutoCompressor(p);
                    case "fixlore" -> handleFixLore(p);
                }
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("reload"));
        b.then(b.arg("compressor")
                .then(b.arg("add", "remove")));
        b.then(b.arg("autocompressor")
                .then(b.arg("apply", "fixlore")));
    }

    private void handleApplyAutoCompressor(Player p) {
        ItemStack hand = p.getInventory().getItemInMainHand();
        ItemMeta meta = hand.getItemMeta();
        String prettyItem = hand.getType().toString().toLowerCase().replace("_"," ");
        if (hand.hasItemMeta() &&
                meta.hasCustomModelData() &&
                meta.getCustomModelData() == 1111) {
            p.sendMessage(Component
                    .text(color(YessirBox.config.prefix + "Your &b" + prettyItem + "&7 is already an autoCompressing tool! (Click to fix lore)"))
                    .hoverEvent(HoverEvent.showText(Component.text(color("&c&lThis will clear all item lore!"))))
                    .clickEvent(ClickEvent.runCommand("/ysb autocompressor fixlore")));
            return;
        }
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
        }
        lore.add(color("&7AutoCompressing I"));
        meta.setLore(lore);
        meta.setCustomModelData(1111);
        hand.setItemMeta(meta);
        info(p,color(YessirBox.config.prefix + "Added the autoCompressing enchantment to your &b" + prettyItem));
    }
    private void handleFixLore(Player p) {
        ItemStack hand = p.getInventory().getItemInMainHand();
        ItemMeta meta = hand.getItemMeta();
        String prettyItem = hand.getType().toString().toLowerCase().replace("_"," ");
        List<String> lore = new ArrayList<>();
        lore.add(color("&7AutoCompressing I"));
        meta.setLore(lore);
        hand.setItemMeta(meta);
        info(p,color(YessirBox.config.prefix + "Added the lore for autoCompressing to your &b" + prettyItem));
    }
}
