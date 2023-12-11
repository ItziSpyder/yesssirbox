package me.trouper.yessirbox.utils;

import de.myzelyam.api.vanish.VanishAPI;
import io.github.itzispyder.pdk.utils.ServerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class Utils {

    public static String getItemName(ItemStack i) {
        return capitalizeWords(i.getType().name());
    }
    public static String capitalize(String s) {
        if (s.length() <= 1) return s.toUpperCase();
        s = s.toLowerCase();
        return String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1);
    }

    public static String capitalizeWords(String s) {
        s = s.replaceAll("[_-]"," ");
        String[] sArray = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String str : sArray) sb.append(capitalize(str)).append(" ");
        return sb.toString().trim();
    }

    public static String[] unVanishedPlayers() {
        return ServerUtils.players(Utils::isVanished).stream().map(Player::getName).toArray(String[]::new);
    }
    public static boolean isVanished(Player player) {
        return !VanishAPI.isInvisible(player);
    }
}
