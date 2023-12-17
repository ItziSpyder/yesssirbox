package me.trouper.yessirbox.data.guis;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.plugin.builders.ItemBuilder;
import io.github.itzispyder.pdk.plugin.gui.CustomGui;
import io.github.itzispyder.pdk.utils.misc.SoundPlayer;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.data.items.StoreItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StoreGUI implements Global {
    static Global g = Global.instance;

    public static final CustomGui home = CustomGui.create()
            .title(g.color("&bStore &7&l| &#E649FF&lY&#EC4BE1&le&#F34DC4&ls&#F94EA6&ls&#FF5088&li&#D97CA6&lr&#B4A8C4&lB&#8ED3E1&lo&#68FFFF&lx"))
            .size(27)
            .defineMain(e -> {
                e.setCancelled(true);
                if (e.getClickedInventory().getItem(e.getSlot()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                    SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
                    deny.play((Player) e.getWhoClicked());
                }
            })
            .onDefine(e -> {
                for (int i = 0; i < 27; i++) {
                    ItemStack item = new ItemBuilder()
                            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                            .name(g.color("&7Slot: " + i))
                            .build();
                    e.setItem(i, item);
                }
            })
            .define(11, StoreItems.commands, e -> {
                SoundPlayer click = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,1,1F);
                click.play((Player) e.getWhoClicked());
                e.getWhoClicked().openInventory(StoreGUI.commands.getInventory());
            })
            .define(13, StoreItems.ranks, e -> {
                SoundPlayer click = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,1,1F);
                click.play((Player) e.getWhoClicked());
                e.getWhoClicked().openInventory(StoreGUI.ranks.getInventory());
            })
            .define(15, StoreItems.crates, e -> {
                SoundPlayer click = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,1,1F);
                click.play((Player) e.getWhoClicked());
                e.getWhoClicked().openInventory(StoreGUI.crates.getInventory());
            })
            .build();

    public static final CustomGui commands = CustomGui.create()
            .title(g.color("&bCommands &7&l| &#E649FF&lY&#EC4BE1&le&#F34DC4&ls&#F94EA6&ls&#FF5088&li&#D97CA6&lr&#B4A8C4&lB&#8ED3E1&lo&#68FFFF&lx"))
            .size(27)
            .defineMain(e -> {
                e.setCancelled(true);
                if (e.getCursor().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                    SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
                    deny.play((Player) e.getWhoClicked());
                }
            })
            .onDefine(e -> {
                for (int i = 0; i < 27; i++) {
                    ItemStack item = new ItemBuilder()
                            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                            .name(g.color("&7Slot: " + i))
                            .build();
                    e.setItem(i, item);
                }
            })
            .define(11,StoreItems.speedCommand, e -> sendPurchaseLink((Player) e.getWhoClicked(), "&d/speed&7 command", "https://yesssirbox.tebex.io/package/6028365"))
            .define(12,StoreItems.compressCommand, e -> sendPurchaseLink((Player) e.getWhoClicked(), "&d/compress&7 command", "https://yesssirbox.tebex.io/package/5831484"))
            .define(13,StoreItems.autoCompressCommand, e -> sendPurchaseLink((Player) e.getWhoClicked(), "&d/autocompress&7 command", "https://yesssirbox.tebex.io/package/5831489"))
            .define(14,StoreItems.flyCommand, e -> sendPurchaseLink((Player) e.getWhoClicked(), "&d/fly&7 command", "https://yesssirbox.tebex.io/package/5831489"))
            .define(16,StoreItems.home, e -> {
                e.setCancelled(true);
                e.getWhoClicked().openInventory(home.getInventory());
                SoundPlayer click = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,1,1F);
                click.play((Player) e.getWhoClicked());
            })
            .build();

    public static final CustomGui ranks = CustomGui.create()
            .title(g.color("&bRanks &7&l| &#E649FF&lY&#EC4BE1&le&#F34DC4&ls&#F94EA6&ls&#FF5088&li&#D97CA6&lr&#B4A8C4&lB&#8ED3E1&lo&#68FFFF&lx"))
            .size(9 * 5)
            .defineMain(e -> {
                e.setCancelled(true);
                if (e.getClickedInventory().getItem(e.getSlot()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                    SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
                    deny.play((Player) e.getWhoClicked());
                }
            })
            .onDefine(e -> {
                for (int i = 0; i < 9*5; i++) {
                    ItemStack item = new ItemBuilder()
                            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                            .name(g.color("&7Slot: " + i))
                            .build();
                    e.setItem(i, item);
                }
            })
            .define(10,StoreItems.sidijuRank, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&c&lSidiju Rank", "https://yesssirbox.tebex.io/package/5873179"))
            .define(12,StoreItems.spongeRank, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&e&lSponge Rank", "https://yesssirbox.tebex.io/package/6029511"))
            .define(14,StoreItems.godlyRank, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&b&lGodly Rank", "https://yesssirbox.tebex.io/package/5804515"))
            .define(28,StoreItems.desiRank, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&d&lDesi Rank", "https://yesssirbox.tebex.io/package/5770305"))
            .define(30,StoreItems.beaconRank, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&b&lBeacon Rank", "https://yesssirbox.tebex.io/package/5762012"))
            .define(32,StoreItems.potatoRank, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&3&lPotato Rank", "https://yesssirbox.tebex.io/package/5765155"))
            .define(25,StoreItems.home, e -> {
                e.setCancelled(true);
                e.getWhoClicked().openInventory(home.getInventory());
                SoundPlayer click = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,1,1F);
                click.play((Player) e.getWhoClicked());
            })
            .build();

    public static final CustomGui crates = CustomGui.create()
            .title(g.color("&bCrate Keys &7&l| &#E649FF&lY&#EC4BE1&le&#F34DC4&ls&#F94EA6&ls&#FF5088&li&#D97CA6&lr&#B4A8C4&lB&#8ED3E1&lo&#68FFFF&lx"))
            .size(9 * 6)
            .defineMain(e -> {
                e.setCancelled(true);
                if (e.getClickedInventory().getItem(e.getSlot()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                    SoundPlayer deny = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1,1);
                    deny.play((Player) e.getWhoClicked());
                }
            })
            .onDefine(e -> {
                for (int i = 0; i < 9*6; i++) {
                    ItemStack item = new ItemBuilder()
                            .material(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                            .name(g.color("&7Slot: " + i))
                            .build();
                    e.setItem(i, item);
                }
            })
            .define(10,StoreItems.moneyCrate, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&2&lMoney Crate", "https://yesssirbox.tebex.io/package/5914487"))
            .define(20,StoreItems.keyall, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&b&lKeyall (x64)", "https://yesssirbox.tebex.io/package/5914490"))
            .define(30,StoreItems.snowKey, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&f&lSnow Key", "https://yesssirbox.tebex.io/package/5914494"))
            .define(40,StoreItems.concreteKey, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&a&lConcrete Key", "https://yesssirbox.tebex.io/package/5914499"))
            .define(32,StoreItems.talismanKey, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&d&lTalisman Key", "https://yesssirbox.tebex.io/package/5914501"))
            .define(24,StoreItems.sandstoneKey, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&3&lSandstone Key", "https://yesssirbox.tebex.io/package/5914502"))
            .define(16,StoreItems.endKey, e -> sendPurchaseLink((Player) e.getWhoClicked(),"&9&lEnd Key", "https://yesssirbox.tebex.io/package/5914504"))
            .define(13,StoreItems.home, e -> {
                e.setCancelled(true);
                e.getWhoClicked().openInventory(home.getInventory());
                SoundPlayer click = new SoundPlayer(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT,1,1F);
                click.play((Player) e.getWhoClicked());
            })
            .build();

    public static void sendPurchaseLink(Player p, String name, String link) {
        SoundPlayer ding = new SoundPlayer(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE,1,1.4F);
        p.closeInventory();
        ding.play(p);
        p.sendMessage(Component
                .text(g.color(YessirBox.config.prefix + "Click this link to purchase the " + name + "&7. \n&9➥ &b&n" + link))
                .clickEvent(ClickEvent.openUrl(link)));
    }
}
