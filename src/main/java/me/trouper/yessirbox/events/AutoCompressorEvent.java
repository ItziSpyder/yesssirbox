package me.trouper.yessirbox.events;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.commands.AutoCompressorCommand;
import me.trouper.yessirbox.commands.CompressCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoCompressorEvent implements CustomListener, Global {
    public static Map<UUID,Boolean> map = new HashMap<>();
    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (map.get(p.getUniqueId()) && p.hasPermission("yessirbox.toggleautocompress") && !map.containsKey(p.getUniqueId())) {
            p.sendMessage(color(YessirBox.config.prefix + "&cYou have not initialized the AutoCompressor yet! &7Toggle it with /autocompressor"));
            map.put(p.getUniqueId(),false);
        }
    }
    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().isAir()) return;
        ItemStack i = p.getInventory().getItemInMainHand();
        if (!i.getItemMeta().hasCustomModelData() || i.getItemMeta().getCustomModelData() != 1111) return;
        if (!map.containsKey(p.getUniqueId()) && p.hasPermission("yessirbox.toggleautocompress")) {
            p.sendMessage(color(YessirBox.config.prefix + "&cYou have not initialized the AutoCompressor yet! &7Toggle it with /autocompressor"));
            return;
        }
        if (map.get(p.getUniqueId()) && p.hasPermission("yessirbox.toggleautocompress")) {
            CompressCommand.execute(p);
        }
    }
}
