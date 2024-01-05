package me.trouper.yessirbox.utils;

import de.myzelyam.api.vanish.VanishAPI;
import io.github.itzispyder.pdk.utils.ServerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    @SuppressWarnings("deprecation")
    public static float getHealth(Damageable e) {
        return (float) ((e.getHealth() + e.getAbsorptionAmount()) / e.getMaxHealth());
    }
    public static void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Player getNearestPlayer(Location loc) {

        double closestDistanceSquared = Double.MAX_VALUE;
        Player nearestPlayer = null;

        for (Player player : loc.getWorld().getPlayers()) {
            double distanceSquared = player.getLocation().distanceSquared(loc);

            if (distanceSquared < closestDistanceSquared) {
                closestDistanceSquared = distanceSquared;
                nearestPlayer = player;
            }
        }
        //Bukkit.broadcast(Component.text("Found a nearest player (" + nearestPlayer + ")"));
        return nearestPlayer;
    }
}
