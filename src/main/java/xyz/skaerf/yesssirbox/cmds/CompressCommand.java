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

import java.util.HashMap;

public class CompressCommand implements CommandExecutor {

    HashMap<ItemStack, ItemStack> compressTo = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        Player p = (Player) sender;
        if (!p.hasPermission("syesssirbox.compress")) {
            p.sendMessage(Component.text(ChatColor.RED+"Sidiju doesn't think you have the permission for that. ;) you can (/buy) it if you want."));
            return true;
        }
        // TODO AAAAAAAAAAAAAAAAAAAAAA
        compress(p, Material.OAK_LOG, 64, Material.OAK_LOG, 1, Enchantment.DAMAGE_ALL, "Compressed Log");
        precomp(p, Material.SPRUCE_LOG, 1, Material.OAK_LOG, 32);
        compress(p, Material.STONE, 64, Material.STONE, 1, Enchantment.DAMAGE_ALL, "Compressed Stone");
        precomp(p, Material.COBBLESTONE, 1, Material.STONE, 1);
        precomp(p, Material.ANDESITE, 1, Material.STONE, 16);
        compress(p, Material.IRON_INGOT, 64, Material.IRON_INGOT, 1, Enchantment.DAMAGE_ALL, "Compressed Iron");
        precomp(p, Material.RAW_IRON, 1, Material.IRON_INGOT, 2);
        precomp(p, Material.IRON_BLOCK, 1, Material.IRON_INGOT, 9);
        compress(p, Material.OBSIDIAN, 64, Material.OBSIDIAN, 1, Enchantment.DAMAGE_ALL, "Compressed Obsidian");
        precomp(p, Material.CRYING_OBSIDIAN, 1, Material.OBSIDIAN, 5);
        compress(p, Material.NETHERITE_INGOT, 64, Material.NETHERITE_INGOT, 1, Enchantment.DAMAGE_ALL, "Compressed Netherite");
        precomp(p, Material.NETHERITE_BLOCK, 1, Material.NETHERITE_INGOT, 9);
        precomp(p, Material.ANCIENT_DEBRIS, 1, Material.NETHERITE_INGOT, 2);
        compress(p, Material.EMERALD, 64, Material.EMERALD, 1, Enchantment.DAMAGE_ALL, "Compressed Emerald");
        precomp(p, Material.EMERALD_BLOCK, 1, Material.EMERALD, 9);
        compress(p, Material.DIAMOND, 64, Material.DIAMOND, 1, Enchantment.DAMAGE_ALL, "Compressed Diamond");
        precomp(p, Material.DIAMOND_BLOCK, 1, Material.DIAMOND, 9);
        compress(p, Material.TARGET, 64, Material.TARGET, 1, Enchantment.DAMAGE_ALL, "");
        precomp(p, Material.SHROOMLIGHT, 1, Material.TARGET, 32);
        compress(p, Material.WAXED_WEATHERED_CUT_COPPER_STAIRS, 64, Material.WAXED_WEATHERED_CUT_COPPER_STAIRS, 1, Enchantment.DAMAGE_ALL, "Compressed waxed lightly weathered out copper stairs");
        compress(p, Material.RAW_COPPER_BLOCK, 64, Material.RAW_COPPER_BLOCK, 1, Enchantment.DAMAGE_ALL, "Compressed Raw Copper Block");
        precomp(p, Material.RAW_COPPER, 9, Material.RAW_COPPER_BLOCK, 1);
        compress(p, Material.BLACKSTONE, 64, Material.BLACKSTONE, 1, Enchantment.MENDING, "§8Compressed Blackstone");
        precomp(p, Material.POLISHED_BLACKSTONE, 1, Material.BLACKSTONE, 4);
        compress(p, Material.END_STONE, 64, Material.END_STONE, 1, Enchantment.MENDING, "§x§F§B§F§1§3§4§lC§x§F§B§E§D§3§4§lo§x§F§B§E§9§3§4§lm§x§F§B§E§5§3§5§lp§x§F§B§E§1§3§5§lr§x§F§C§D§C§3§5§le§x§F§C§D§8§3§5§ls§x§F§C§D§4§3§5§ls§x§F§C§D§0§3§5§le§x§F§C§C§C§3§6§ld §x§F§C§C§8§3§6§lE§x§F§C§C§4§3§6§ln§x§F§C§C§0§3§6§ld§x§F§D§B§B§3§6§ls§x§F§D§B§7§3§6§lt§x§F§D§B§3§3§7§lo§x§F§D§A§F§3§7§ln§x§F§D§A§B§3§7§le");
        compress(p, Material.END_STONE_BRICKS, 64, Material.END_STONE_BRICKS, 1, Enchantment.MENDING, "§x§F§B§F§1§3§4C§x§F§B§E§E§3§4o§x§F§B§E§B§3§4m§x§F§B§E§7§3§4p§x§F§B§E§4§3§5r§x§F§B§E§1§3§5e§x§F§C§D§E§3§5s§x§F§C§D§B§3§5s§x§F§C§D§8§3§5e§x§F§C§D§4§3§5d §x§F§C§D§1§3§5E§x§F§C§C§E§3§6n§x§F§C§C§B§3§6d§x§F§C§C§8§3§6s§x§F§C§C§4§3§6t§x§F§C§C§1§3§6o§x§F§C§B§E§3§6n§x§F§D§B§B§3§6e §x§F§D§B§8§3§6B§x§F§D§B§5§3§7r§x§F§D§B§1§3§7i§x§F§D§A§E§3§7c§x§F§D§A§B§3§7k");
        compress(p, Material.PURPUR_BLOCK, 64, Material.PURPUR_BLOCK, 1, Enchantment.MENDING, "§x§F§B§1§3§E§7§lC§x§F§3§1§3§D§F§lo§x§E§B§1§2§D§7§lm§x§E§3§1§2§D§0§lp§x§D§B§1§1§C§8§lr§x§D§3§1§1§C§0§le§x§C§B§1§1§B§8§ls§x§C§3§1§0§B§0§ls§x§B§B§1§0§A§9§le§x§B§3§0§F§A§1§ld §x§A§B§0§F§9§9§lP§x§A§3§0§F§9§1§lu§x§9§B§0§E§8§9§lr§x§9§3§0§E§8§2§lp§x§8§B§0§D§7§A§lu§x§8§3§0§D§7§2§lr");
        precomp(p, Material.PURPUR_PILLAR, 1, Material.PURPUR_BLOCK, 4);
        compress(p, Material.RESPAWN_ANCHOR, 64, Material.RESPAWN_ANCHOR, 1, Enchantment.MENDING, "§x§D§3§8§2§F§B§lC§x§D§6§7§B§F§9§lo§x§D§9§7§4§F§7§lm§x§D§C§6§D§F§5§lp§x§D§F§6§6§F§3§lr§x§E§2§5§F§F§1§le§x§E§5§5§8§E§F§ls§x§E§8§5§1§E§D§ls§x§E§A§4§9§E§B§le§x§E§D§4§2§E§9§ld §x§F§0§3§B§E§7§lA§x§F§3§3§4§E§5§ln§x§F§6§2§D§E§3§lc§x§F§9§2§6§E§1§lh§x§F§C§1§F§D§F§lo§x§F§F§1§8§D§D§lr");
        compress(p, Material.PURPLE_WOOL, 64, Material.PURPLE_WOOL, 1, Enchantment.MENDING, "§x§E§4§7§E§F§F§lC§x§D§E§7§9§F§8§lo§x§D§8§7§5§F§2§lm§x§D§1§7§0§E§B§lp§x§C§B§6§B§E§5§lr§x§C§5§6§7§D§E§le§x§B§F§6§2§D§7§ls§x§B§8§5§D§D§1§ls§x§B§2§5§8§C§A§le§x§A§C§5§4§C§3§ld §x§A§6§4§F§B§D§lW§x§9§F§4§A§B§6§lo§x§9§9§4§6§B§0§lo§x§9§3§4§1§A§9§ll");
        precomp(p, Material.PINK_WOOL, 1, Material.PURPLE_WOOL, 16);
        compress(p, Material.PURPLE_STAINED_GLASS, 64, Material.PURPLE_STAINED_GLASS, 1, Enchantment.MENDING, "§x§9§A§4§E§F§F§lC§x§9§5§4§C§F§A§lo§x§9§1§4§9§F§5§lm§x§8§C§4§7§F§0§lp§x§8§7§4§5§E§B§lr§x§8§2§4§2§E§6§le§x§7§E§4§0§E§1§ls§x§7§9§3§E§D§C§ls§x§7§4§3§B§D§7§le§x§7§0§3§9§D§2§ld §x§6§B§3§6§C§D§lG§x§6§6§3§4§C§8§ll§x§6§1§3§2§C§3§la§x§5§D§2§F§B§E§ls§x§5§8§2§D§B§9§ls");
        precomp(p, Material.PINK_STAINED_GLASS, 1, Material.PURPLE_STAINED_GLASS, 16);
        compress(p, Material.AMETHYST_BLOCK, 64, Material.AMETHYST_BLOCK, 1, Enchantment.MENDING, "§x§F§F§0§0§F§8§lC§x§F§7§0§4§F§8§lo§x§E§F§0§7§F§9§lm§x§E§8§0§B§F§9§lp§x§E§0§0§F§F§A§lr§x§D§8§1§3§F§A§le§x§D§0§1§6§F§A§ls§x§C§8§1§A§F§B§ls§x§C§0§1§E§F§B§le§x§B§9§2§1§F§C§ld §x§B§1§2§5§F§C§lA§x§A§9§2§9§F§D§lm§x§A§1§2§C§F§D§le§x§9§9§3§0§F§D§lt§x§9§1§3§4§F§E§lh§x§8§A§3§8§F§E§ly§x§8§2§3§B§F§F§ls§x§7§A§3§F§F§F§lt");
        compress(p, Material.SANDSTONE, 64, Material.SANDSTONE, 1, Enchantment.DAMAGE_ALL, "Compressed Sandstone");
        dcompress(p, Material.SANDSTONE, 64, "Compressed Sandstone", Material.SMOOTH_RED_SANDSTONE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Sandstone");
        compress(p, Material.DRIPSTONE_BLOCK, 64, Material.DRIPSTONE_BLOCK, 1, Enchantment.DAMAGE_ALL, "Compressed Dripstone");
        dcompress(p, Material.DRIPSTONE_BLOCK, 64, "Compressed Dripstone", Material.POLISHED_GRANITE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Dripstone");
        compress(p, Material.SNOW_BLOCK, 64, Material.SNOW_BLOCK, 1, Enchantment.DAMAGE_ALL, "Compressed Snow");
        compress(p, Material.QUARTZ_BLOCK, 64, Material.QUARTZ_BLOCK, 1, Enchantment.DAMAGE_ALL, "Compressed Quartz");
        dcompress(p, Material.QUARTZ_BLOCK, 64, "Compressed Quartz", Material.QUARTZ_BLOCK, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Quartz");
        compress(p, Material.LIGHT_BLUE_CONCRETE, 32, Material.LIGHT_BLUE_CONCRETE, 1, Enchantment.DAMAGE_ALL, "Compressed Light Blue Concrete");
        dcompress(p, Material.LIGHT_BLUE_CONCRETE, 32, "Compressed Light Blue Concrete", Material.LIGHT_BLUE_CONCRETE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Light Blue Concrete");
        dcompress(p, Material.LIGHT_BLUE_CONCRETE, 2, "Compressed Compressed Light Blue Concrete", Material.BLUE_CONCRETE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Compressed Concrete");
        compress(p, Material.BIRCH_PLANKS, 64, Material.BIRCH_PLANKS, 1, Enchantment.DAMAGE_ALL, "Compressed Birch Planks");
        dcompress(p, Material.BIRCH_PLANKS, 40, "Compressed Birch Planks", Material.BIRCH_PLANKS, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Birch Planks");
        compress(p, Material.PACKED_ICE, 64, Material.PACKED_ICE, 1, Enchantment.DAMAGE_ALL, "Compressed Packed Ice");
        dcompress(p, Material.PACKED_ICE, 20, "Compressed Packed Ice", Material.PACKED_ICE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Packed Ice");
        dcompress(p, Material.PACKED_ICE, 10, "Compressed Compressed Packed Ice", Material.PACKED_ICE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Compressed Packed Ice");
        compress(p, Material.STRIPPED_OAK_LOG, 64, Material.STRIPPED_OAK_LOG, 1, Enchantment.DAMAGE_ALL, "Compressed Potato");
        dcompress(p, Material.STRIPPED_OAK_LOG, 64, "Compressed Potato", Material.STRIPPED_OAK_LOG, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Potato");
        compress(p, Material.RED_TERRACOTTA, 64, Material.RED_TERRACOTTA, 1, Enchantment.DAMAGE_ALL, "Compressed Terracotta");
        dcompress(p, Material.RED_TERRACOTTA, 32, "Compressed Terracotta", Material.REDSTONE_ORE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Terracotta");
        compress(p, Material.RED_GLAZED_TERRACOTTA, 64, Material.RED_GLAZED_TERRACOTTA, 1, Enchantment.DAMAGE_ALL, "Compressed Glazed Terracotta");
        dcompress(p, Material.RED_GLAZED_TERRACOTTA, 64, "Compressed Glazed Terracotta", Material.RED_GLAZED_TERRACOTTA, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Glazed Terracotta");
        compress(p, Material.BONE_BLOCK, 64,Material.BONE_BLOCK,1, Enchantment.DAMAGE_ALL,"Compressed Bone Block");
        dcompress(p, Material.BONE_BLOCK, 16, "Compressed Bone Block", Material.BONE_BLOCK, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Bone Block");
        precomp(p, Material.GOLD_ORE, 1, Material.RAW_GOLD, 2);
        precomp(p, Material.GOLD_BLOCK, 1, Material.RAW_GOLD, 9);
        compress(p, Material.RAW_GOLD, 31, Material.RAW_GOLD, 1, Enchantment.DAMAGE_ALL, "Compressed Raw Gold");
        dcompress(p, Material.RAW_GOLD, 16, "Compressed Raw Gold", Material.GOLD_INGOT, 1, Enchantment.DAMAGE_ALL, "Compressed Gold");
        dcompress(p, Material.GOLD_INGOT, 8, "Compressed Gold", Material.GOLD_INGOT, 1, Enchantment.DAMAGE_ALL, "Ultra Compressed Gold");
        compress(p, Material.RED_SANDSTONE, 32, Material.RED_SANDSTONE, 1, Enchantment.DAMAGE_ALL, "Compressed Red Sandstone");
        dcompress(p, Material.RED_SANDSTONE, 16, "Compressed Red Sandstone", Material.CHISELED_RED_SANDSTONE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Red Sandstone");
        dcompress(p, Material.CHISELED_RED_SANDSTONE, 8, "Compressed Compressed Red Sandstone", Material.CUT_RED_SANDSTONE, 1, Enchantment.DAMAGE_ALL, "Ultra Compressed Red Sandstone");
        compress(p, Material.SOUL_SAND, 16, Material.SOUL_SAND, 1, Enchantment.DAMAGE_ALL, "Compressed Soulsand");
        dcompress(p, Material.SOUL_SAND, 8, "Compressed Soulsand", Material.SOUL_SAND, 1, Enchantment.DAMAGE_ALL,"Compressed Compressed Soulsand");
        dcompress(p, Material.SOUL_SAND, 8, "Compressed Compressed Soulsand", Material.SOUL_SAND, 1, Enchantment.DAMAGE_ALL,"Ultra Compressed Soulsand");
        compress(p, Material.SPRUCE_LEAVES, 16, Material.SPRUCE_LEAVES, 1, Enchantment.DAMAGE_ALL, "Compressed Spruce Leaves");
        dcompress(p, Material.SPRUCE_LEAVES, 16, "Compressed Spruce Leaves", Material.SPRUCE_LEAVES, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Spruce Leaves");
        dcompress(p, Material.SPRUCE_LEAVES, 8, "Compressed Compressed Spruce Leaves", Material.SPRUCE_LEAVES, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Compressed Spruce Leaves");
        dcompress(p, Material.SPRUCE_LEAVES, 2, "Compressed Compressed Compressed Spruce Leaves", Material.SPRUCE_LEAVES, 1, Enchantment.DAMAGE_ALL, "Ultra Compressed Spruce Leaves");
        precomp(p, Material.GLOWSTONE, 1, Material.GLOWSTONE_DUST, 4);
        compress(p, Material.GLOWSTONE_DUST, 64, Material.GLOWSTONE_DUST, 1, Enchantment.DAMAGE_ALL, "Compressed Glowstone Dust");
        dcompress(p, Material.GLOWSTONE_DUST, 32, "Compressed Glowstone Dust", Material.GLOWSTONE, 1, Enchantment.DAMAGE_ALL, "Compressed Glowstone");
        dcompress(p, Material.GLOWSTONE, 16, "Compressed Glowstone", Material.GLOWSTONE, 1, Enchantment.DAMAGE_ALL, "Compressed Compressed Glowstone");
        compress(p, Material.DIRT, 61, Material.DIRT, 1, Enchantment.DAMAGE_ALL, "§cCompressed Sidiju");
        p.sendMessage(ChatColor.GREEN + "Your inventory has been compressed.");
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
