package xyz.skaerf.yesssirbox.commands.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.skaerf.yesssirbox.YSBItemStack;
import xyz.skaerf.yesssirbox.Yesssirbox;
import xyz.skaerf.yesssirbox.commands.Args;
import xyz.skaerf.yesssirbox.commands.Command;
import xyz.skaerf.yesssirbox.commands.CommandName;
import xyz.skaerf.yesssirbox.commands.completions.CompletionBuilder;

import java.util.*;

@CommandName("shop")
public class ShopCommand implements Command {

    private static final Component shopInvName = Component.text("Shop");
    private static final HashMap<Integer, YSBItemStack> shopItems = new HashMap<>();
    private static final HashMap<ItemStack, YSBItemStack> toYSB = new HashMap<>();
    private static final HashMap<ItemStack, Boolean> canTheyHaveIt = new HashMap<>();

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("go away");
            return;
        }
        if (args.getSize() != 0 && sender.hasPermission("yesssirbox.admin")) {
            if (args.getSize() == 4 && player.getInventory().getItem(0) != null) {
                ItemStack inh = ((Player) sender).getInventory().getItem(0);
                assert inh != null;
                YSBItemStack item = new YSBItemStack(inh.getType(), inh.getAmount());
                item.setItemMeta(inh.getItemMeta());
                List<ItemStack> toCraft = new ArrayList<>();
                for (int i = 1; i < args.get(3).intValue(); i++) {
                    ItemStack it = ((Player) sender).getInventory().getItem(i);
                    if (it != null) toCraft.add(it);
                }
                item.setRequiredToCraft(toCraft);
                int slot;
                double cost;
                try {
                    slot = args.get(1).intValue();
                    cost = args.get(2).doubleValue();
                }
                catch (NumberFormatException e) {
                    displayShop((Player)sender);
                    return;
                }
                item.setValue(cost);
                this.saveItemToConfig(item, slot);
                sender.sendMessage(ChatColor.GREEN+"New item saved to config. Please reload the plugin's config (/ysb reload) to apply it to the shop GUI.");
            }
        }
        displayShop((Player)sender);
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }

    private void saveItemToConfig(YSBItemStack item, int location) {
        JavaPlugin pl = Yesssirbox.getPlugin(Yesssirbox.class);
        List<String> savedItems = pl.getConfig().getStringList("shopItems");
        String newLine = "";
        // lists are split with ][
        // type::amount::locInShopInv::displayName::lore][::cost::requiredToCraft][::enchants][
        StringBuilder lore = new StringBuilder();
        if (item.getItemMeta().lore() != null) for (Component lor : Objects.requireNonNull(item.getItemMeta().lore())) {
            lore.append(PlainTextComponentSerializer.plainText().serialize(lor) + "][");
        }
        lore = new StringBuilder(lore.substring(0, lore.length() - 2));
        StringBuilder requiredToCraft = new StringBuilder();
        for (ItemStack it : item.getRequiredToCraft()) {
            requiredToCraft.append(it.getType()+","+it.getAmount()+"][");
        }
        requiredToCraft = new StringBuilder(requiredToCraft.substring(0, requiredToCraft.length() - 2));
        StringBuilder enchants = new StringBuilder();
        for (Enchantment ench : item.getEnchantments().keySet()) {
            enchants.append(ench.getKey()+","+item.getEnchantmentLevel(ench)+"][");
        }
        enchants = new StringBuilder(enchants.substring(0, enchants.length() - 2));
        String name = "0";

        newLine = newLine + item.getType() + "::" + item.getAmount() + "::" + location + "::" + name + "::" + lore + "::" + item.getValue() + "::" + requiredToCraft + "::" + enchants;

        savedItems.add(newLine);
        pl.getConfig().set("shopItems", savedItems);
        pl.saveConfig();
    }

    private static void displayShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 54, shopInvName);

        // WOOD AXE
        for (int key : shopItems.keySet()) {
            YSBItemStack item = shopItems.get(key);
            List<Component> uneditedLore = item.lore();
            item.lore(item.addDependentLore(item.lore(), player));
            if (!item.canAfford(player) || !item.canCraft(player)) {
                canTheyHaveIt.put(item.toItemStack(), false);
            }
            else {
                canTheyHaveIt.put(item.toItemStack(), true);
            }
            shop.setItem(key, item.toItemStack());
            toYSB.put(item.toItemStack(), item);
            // revert lore after adding it to inventory
            item.lore(uneditedLore);
        }

        player.openInventory(shop);
    }

    public static Component getShopInvName() {
        return shopInvName;
    }

    public static void setItems(FileConfiguration config) {
        List<String> items = config.getStringList("shopItems");
        for (String i : items) {
            // lists are split with ][
            // type::amount::locInShopInv::displayName::lore][::cost::requiredToCraft][::enchants][
            String[] vars = i.split("::");
            int amount = Integer.parseInt(vars[1]);
            int slot = Integer.parseInt(vars[2]);
            double value = Double.parseDouble(vars[5]);

            String name = vars[3];
            List<Component> lore = new ArrayList<>();
            for (String a : vars[4].split("]\\[")) {
                lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', a)));
            }
            List<ItemStack> requiredToCraft = new ArrayList<>();
            for (String j : vars[6].split("]\\[")) {
                requiredToCraft.add(new ItemStack(Material.valueOf(j.split(",")[0]), Integer.parseInt(j.split(",")[1])));
            }
            Map<Enchantment, Integer> enchants = new HashMap<>();
            for (String ench : vars[7].split("]\\[")) {
                Enchantment enchValue = Enchantment.getByKey(NamespacedKey.fromString(ench.split(",")[0]));
                int level = Integer.parseInt(ench.split(",")[1]);
                enchants.put(enchValue, level);
            }
            YSBItemStack toAdd = new YSBItemStack(Material.valueOf(vars[0]), amount);
            toAdd.addUnsafeEnchantments(enchants);
            ItemMeta meta = toAdd.getItemMeta();
            if (!name.equals("0")) meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
            toAdd.setRequiredToCraft(requiredToCraft);
            toAdd.setValue(value);
            meta.lore(lore);
            toAdd.setItemMeta(meta);
            shopItems.put(slot, toAdd);
        }
    }

    public static void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) return;
        event.setCancelled(true);
        if (!canTheyHaveIt.containsKey(itemStack)) return;
        if (canTheyHaveIt.get(itemStack)) {
            YSBItemStack item = toYSB.get(itemStack);
            EconomyResponse res = Yesssirbox.econ.withdrawPlayer(player, item.getValue());
            if (res.transactionSuccess()) {
                for (ItemStack remove : item.getRequiredToCraft()) {
                    player.getInventory().removeItem(remove);
                }
                player.updateInventory();
                int iterator = 0;
                for (ItemStack i : player.getInventory()) {
                    if (i == null) {
                        player.getInventory().setItem(iterator, item);
                        player.sendMessage(ChatColor.GREEN+"Item is in your inventory!");
                        player.closeInventory();
                        displayShop(player);
                        break;
                    }
                    iterator++;
                }
                player.updateInventory();
            }
            else {
                player.sendMessage(ChatColor.RED+"Payment did not go through.");
            }
        }
    }
}
