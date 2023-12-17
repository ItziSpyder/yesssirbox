package me.trouper.yessirbox.data.items;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class StoreItems implements Global {
    static Global g = Global.instance;
    public static final ItemStack blank = new ItemBuilder()
            .name(g.color("&f"))
            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .build();
    // MAIN STUFF
    public static final ItemStack home = Bukkit.getItemFactory().createItemStack("player_head{SkullOwner:{Id:[I;891982802,1654277952,-1266198704,-1544975137],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=\"}]}},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_blue\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Click to return to the home page\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":true,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"blue\",\"text\":\"Home\"}],\"text\":\"\"}'}}");
    public static final ItemStack ranks = Bukkit.getItemFactory().createItemStack("player_head{SkullOwner:{Id:[I;-831940828,-1237954722,-2113449700,-1011335812],Name:\"Crown\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJmYWYyYWU0NWViM2JiYzQxMDU3MjlkYTE5YjViM2UyZjExMTU5ZGMwZTU3ZDlhMTJmZjk1MzcxYmExODNjZiJ9fX0=\"}]}},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_gray\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Sponge\"}],\"text\":\"\"}','{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_gray\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Sidiju\"}],\"text\":\"\"}','{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_gray\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Godly\"}],\"text\":\"\"}','{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_gray\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Beacon\"}],\"text\":\"\"}','{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_gray\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Desi\"}],\"text\":\"\"}','{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_gray\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Potato\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":true,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"gold\",\"text\":\"Ranks\"}],\"text\":\"\"}'}}");
    public static final ItemStack crates = Bukkit.getItemFactory().createItemStack("player_head{SkullOwner:{Id:[I;2088445020,2026588190,-1123594741,2095845784],Name:\"Gold Chest\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ0NDk4YTBmZTI3ODk1NmUzZDA0MTM1ZWY0YjEzNDNkMDU0OGE3ZTIwOGM2MWIxZmI2ZjNiNGRiYzI0MGRhOCJ9fX0=\"}]}},display:{Lore:['{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Money Crate\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Keyall (x64)\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Snow Key\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Concrete Key\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Talisman Key\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"Sandstone Key\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":false,\"color\":\"gold\",\"text\":\"➥ \"},{\"italic\":false,\"color\":\"gray\",\"text\":\"End Key\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":true,\"italic\":false,\"color\":\"yellow\",\"text\":\"Crates\"}],\"text\":\"\"}'}}");
    public static final ItemStack commands = Bukkit.getItemFactory().createItemStack("player_head{SkullOwner:{Id:[I;-474694099,-1588377440,-1215522173,1528128623],Name:\"Terminal\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIyODcyMzQyZDJjZjIwNzU0YjllMWJhZTljMDkwMjkxMmRjYWUxMmU2M2I1MjBiNmZlOGJkOTExYjkxMDE4YiJ9fX0=\"}]}},display:{Lore:['{\"extra\":[{\"italic\":true,\"color\":\"dark_purple\",\"text\":\"➥ \"},{\"italic\":true,\"color\":\"gray\",\"text\":\"/speed\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":true,\"color\":\"dark_purple\",\"text\":\"➥ \"},{\"italic\":true,\"color\":\"gray\",\"text\":\"/compress\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":true,\"color\":\"dark_purple\",\"text\":\"➥ \"},{\"italic\":true,\"color\":\"gray\",\"text\":\"/autocompress\"}],\"text\":\"\"}','{\"extra\":[{\"italic\":true,\"color\":\"dark_purple\",\"text\":\"➥ \"},{\"italic\":true,\"color\":\"gray\",\"text\":\"/fly\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":true,\"italic\":false,\"color\":\"light_purple\",\"text\":\"Commands\"}],\"text\":\"\"}'}}");

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                                                    Commands
     =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */
    public static final ItemStack speedCommand = new ItemBuilder()
            .name(g.color("&b/speed"))
            .lore(g.color("&9➥&7 Ability to change walk/fly speed"))
            .lore("")
            .lore(g.color("&7Price: &2$5.99"))
            .material(Material.POTION)
            .flag(ItemFlag.HIDE_ITEM_SPECIFICS)
            .build();
    public static final ItemStack compressCommand = new ItemBuilder()
            .name(g.color("&b/compress"))
            .lore(g.color("&9➥&7 Fully compress your whole inventory in one command"))
            .lore("")
            .lore(g.color("&7Price: &2$2.99"))
            .material(Material.PISTON)
            .flag(ItemFlag.HIDE_ITEM_SPECIFICS)
            .build();
    public static final ItemStack autoCompressCommand = new ItemBuilder()
            .name(g.color("&b/autocompressor"))
            .lore(g.color("&9➥&7 Compress your inventory every time you mine"))
            .lore("")
            .lore(g.color("&7Price: &2$4.99"))
            .material(Material.STICKY_PISTON)
            .flag(ItemFlag.HIDE_ITEM_SPECIFICS)
            .build();
    public static final ItemStack flyCommand = new ItemBuilder()
            .name(g.color("&b/fly"))
            .lore(g.color("&9➥&7 Ability to fly"))
            .lore("")
            .lore(g.color("&7Price: &2$4.99"))
            .material(Material.FEATHER)
            .flag(ItemFlag.HIDE_ITEM_SPECIFICS)
            .build();

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                                               Ranks
     =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public static final ItemStack spongeRank = new ItemBuilder()
            .name(g.color("&e&lSponge"))
            .lore(g.color("&6➥&7 /warp sponge"))
            .lore(g.color("&6➥&7 /warp beacon"))
            .lore(g.color("&6➥&7 /warp potato"))
            .lore(g.color("&6➥&7 /autocompress"))
            .lore(g.color("&6➥&7 /compress"))
            .lore(g.color("&6➥&7 /pv 3-10"))
            .lore(g.color("&6➥&7 1,000,000 in-game currency"))
            .lore("")
            .lore(g.color("&7Price: &2$39.99"))
            .material(Material.SPONGE)
            .enchant(Enchantment.LURE,1)
            .flag(ItemFlag.HIDE_ENCHANTS)
            .build();

    public static final ItemStack sidijuRank = new ItemBuilder()
            .name(g.color("&c&lSidiju"))
            .lore(g.color("&4➥&7 /warp sidiju"))
            .lore(g.color("&4➥&7 /warp desi"))
            .lore(g.color("&4➥&7 /warp beacon"))
            .lore(g.color("&4➥&7 /warp potato"))
            .lore(g.color("&4➥&7 /autocompress"))
            .lore(g.color("&4➥&7 /compress"))
            .lore(g.color("&4➥&7 /pv 3-10"))
            .lore(g.color("&4➥&7 100,000 in-game currency"))
            .lore("")
            .lore(g.color("&7Price: &2$38.99"))
            .material(Material.IRON_SWORD)
            .enchant(Enchantment.LURE,1)
            .flag(ItemFlag.HIDE_ENCHANTS)
            .build();

    public static final ItemStack godlyRank = new ItemBuilder()
            .name(g.color("&b&lGodly"))
            .lore(g.color("&1➥&7 /warp desi"))
            .lore(g.color("&1➥&7 /warp beacon"))
            .lore(g.color("&1➥&7 /warp potato"))
            .lore(g.color("&1➥&7 /autocompress"))
            .lore(g.color("&1➥&7 /compress"))
            .lore(g.color("&1➥&7 /pv 3-10"))
            .lore(g.color("&1➥&7 100,000 in-game currency"))
            .lore("")
            .lore(g.color("&7Price: &2$27.99"))
            .material(Material.NETHER_STAR)
            .enchant(Enchantment.LURE,1)
            .flag(ItemFlag.HIDE_ENCHANTS)
            .build();

    public static final ItemStack desiRank = new ItemBuilder()
            .name(g.color("&d&lDesi"))
            .lore(g.color("&5➥&7 /warp desi"))
            .lore(g.color("&5➥&7 /autocompress"))
            .lore(g.color("&5➥&7 /compress"))
            .lore(g.color("&5➥&7 /pv 3-7"))
            .lore("")
            .lore(g.color("&7Price: &2$11.99"))
            .material(Material.GOLD_INGOT)
            .enchant(Enchantment.LURE,1)
            .flag(ItemFlag.HIDE_ENCHANTS)
            .build();

    public static final ItemStack potatoRank = new ItemBuilder()
            .name(g.color("&3&lPotato"))
            .lore(g.color("&5➥&7 /warp potato"))
            .lore(g.color("&5➥&7 /compress"))
            .lore(g.color("&5➥&7 /pv 3-6"))
            .lore("")
            .lore(g.color("&7Price: &2$9.99"))
            .material(Material.POTATO)
            .enchant(Enchantment.LURE,1)
            .flag(ItemFlag.HIDE_ENCHANTS)
            .build();

    public static final ItemStack beaconRank = new ItemBuilder()
            .name(g.color("&b&lBeacon"))
            .lore(g.color("&5➥&7 /warp beacon"))
            .lore(g.color("&5➥&7 /compress"))
            .lore(g.color("&5➥&7 /pv 3-4"))
            .lore("")
            .lore(g.color("&7Price: &2$9.00"))
            .material(Material.BEACON)
            .enchant(Enchantment.LURE,1)
            .flag(ItemFlag.HIDE_ENCHANTS)
            .build();

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                                               Crate Keys
     =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public static final ItemStack moneyCrate = new ItemBuilder()
            .name(g.color("&e&lMoney Crate"))
            .lore("")
            .lore(g.color("&7Price: &2$2.50"))
            .material(Material.GOLD_NUGGET)
            .build();

    public static final ItemStack keyall = new ItemBuilder()
            .name(g.color("&b&lKeyall (x64)"))
            .lore("")
            .lore(g.color("&7Price: &2$3.50"))
            .material(Material.TRIPWIRE_HOOK)
            .build();

    public static final ItemStack snowKey = new ItemBuilder()
            .name(g.color("&f&lSnow Key"))
            .lore("")
            .lore(g.color("&7Price: &2$1.50"))
            .material(Material.SNOW_BLOCK)
            .build();

    public static final ItemStack concreteKey = new ItemBuilder()
            .name(g.color("&a&lConcrete Key"))
            .lore("")
            .lore(g.color("&7Price: &2$2.00"))
            .material(Material.LIME_CONCRETE)
            .build();

    public static final ItemStack talismanKey = new ItemBuilder()
            .name(g.color("&d&lTalisman Key"))
            .lore("")
            .lore(g.color("&7Price: &2$1.00"))
            .material(Material.TOTEM_OF_UNDYING)
            .build();

    public static final ItemStack sandstoneKey = new ItemBuilder()
            .name(g.color("&e&lSandstone Key"))
            .lore("")
            .lore(g.color("&7Price: &2$0.75"))
            .material(Material.SANDSTONE)
            .build();

    public static final ItemStack endKey = new ItemBuilder()
            .name(g.color("&9&lEnd Key"))
            .lore("")
            .lore(g.color("&7Price: &2$0.50"))
            .material(Material.ENDER_EYE)
            .build();


}
