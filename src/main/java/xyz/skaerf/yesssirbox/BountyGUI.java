package xyz.skaerf.yesssirbox;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class BountyGUI {

    public void showNewBountyGUI(Player toPlayer) {
        Inventory inventory = Bukkit.createInventory(null, 36, Component.text("Bounties - Online Players"));
        int slot = 0;
        List<String> bounties = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("bounties");
        for (String bounty : bounties) {
            UUID uuid = UUID.fromString(bounty.split(":")[0]);
            double value = Double.parseDouble(bounty.split(":")[1]);
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM, 1);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            meta.displayName(Component.text(ChatColor.GREEN+player.getName()+" - $"+value));
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
            slot++;
        }
    }
}
