package xyz.skaerf.yesssirbox.cmds;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.skaerf.yesssirbox.Compressable;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.HashMap;
import java.util.List;

public class CompressCommand implements CommandExecutor {

    HashMap<ItemStack, ItemStack> compressTo = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("syesssirbox.compress")) {
            player.sendMessage(Component.text(ChatColor.RED+"You don't have permission to use compression! You can buy it on the store though. /buy"));
            return true;
        }
        compress(player);
        player.sendMessage(ChatColor.GREEN + "Your inventory has been compressed.");
        return false;
    }


    public void dcompress (Player player, Material mat1, int a, String name1,  Material mat2, int b, Enchantment ench2, String name2){
        // double compress - Comp to CompComp or CompCompComp etc
        for (ItemStack stack : player.getInventory()) {
            if (stack != null && stack.getType() == mat1 && stack.getItemMeta().getDisplayName().equals(name1) && stack.getAmount() >= a) {
                while (stack.getAmount() >= a) {
                    stack.setAmount(stack.getAmount() - a);
                    ItemStack compressed_material = new ItemStack(mat2, b);
                    compressed_material.addUnsafeEnchantment(ench2, 1);
                    ItemMeta compressed_materialmeta = compressed_material.getItemMeta();
                    compressed_materialmeta.setDisplayName(name2);
                    compressed_material.setItemMeta(compressed_materialmeta);
                    player.getInventory().addItem(compressed_material);
                }
            }
        }
    }

    public void compress(Player player) {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack != null && !stack.getType().equals(Material.AIR) && Compressable.isCompressable(stack)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // the reason this is a list is to ensure that if the entire stack is not compressed, it is not all taken
                        List<ItemStack> replacement = Compressable.compress(stack);
                        if (replacement != null) {
                            player.getInventory().remove(replacement.get(0));
                            player.getInventory().addItem(replacement.get(1));
                        }
                    }
                }.runTask(Yesssirbox.getPlugin(Yesssirbox.class));
            }
        }
    }

    public void compress (Player player, Material mat1, int a,  Material mat2, int b, Enchantment ench, String name){
        // compress first time e.g. oak log into comp oak log
        for (ItemStack stack : player.getInventory()) {
            if (stack != null && stack.getType() == mat1 && !stack.getItemMeta().hasEnchants() && stack.getAmount() >= a) {
                while (stack.getAmount() >= a) {
                    stack.setAmount(stack.getAmount() - a);
                    ItemStack compressed_material = new ItemStack(mat2, b);
                    compressed_material.addUnsafeEnchantment(ench, 1);
                    ItemMeta compressed_materialmeta = compressed_material.getItemMeta();
                    compressed_materialmeta.setDisplayName(name);
                    compressed_material.setItemMeta(compressed_materialmeta);
                    player.getInventory().addItem(compressed_material);
                }
            }
        }
    }

    public void precomp (Player player, Material mat1, int a, Material mat2, int b){
        // special material to standard e.g. cobble to 16 stone
        while (player.getInventory().contains(mat1, a)) {
            ItemStack compress_material = new ItemStack(mat1, a);
            player.getInventory().removeItem(compress_material);
            ItemStack compressed_material = new ItemStack(mat2, b);
            player.getInventory().addItem(compressed_material);
        }
    }

}
