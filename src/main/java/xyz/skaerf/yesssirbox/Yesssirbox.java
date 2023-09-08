package xyz.skaerf.yesssirbox;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.skaerf.yesssirbox.cmds.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


public final class Yesssirbox extends JavaPlugin {

    public static HashMap<Material, Double> blockValues = new HashMap<>();
    private static HashMap<Player, String> actionBars = new HashMap<>();
    private static HashMap<UUID, Long> dailies = new HashMap<>();
    private static List<String> blockedWords = new ArrayList<>();

    private static HashMap<ItemStack, ItemStack> compressables = new HashMap<>();
    public static Economy econ;
    private static YamlConfiguration compConf;
    private static YamlConfiguration blockedWordsConf;


    @Override
    public void onEnable() {
        Events.fillArmorLists();
        getServer().getPluginManager().registerEvents(new Events(), this);
        this.saveDefaultConfig();
        refreshBlockValues();
        setupEconomy();
        getCommand("yesssirbox").setExecutor(new YesssirboxCommand());
        getCommand("autocompressor").setExecutor(new AutoCompressorCommand());
        getCommand("autocompress").setExecutor(new AutoCompressCommand());
        getCommand("compress").setExecutor(new CompressCommand());
        getCommand("vote").setExecutor(new VoteCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("bounty").setExecutor(new BountyCommand());
        getCommand("offhand").setExecutor(new OffhandCommand());
        getCommand("daily").setExecutor(new DailyCommand());
        ShopCommand.setItems(this.getConfig());
        setDailies();
        reloadAllConfigs();
        loadCompressables();
        loadBlockedWords();
    }

    @Override
    public void onDisable() {
        saveDailies();
        saveConfig();
        saveCompressables();
    }

    public static HashMap<ItemStack, ItemStack> getCompressables() {
        return compressables;
    }

    public static void loadCompressables() {
        List<ItemStack> keys = (List<ItemStack>) compConf.getList("keySet");
        List<ItemStack> values = (List<ItemStack>) compConf.getList("valueSet");
        if (keys == null || values == null) return;
        for (int i = 0; i < keys.size(); i++) {
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(keys.get(i).getType()+", "+keys.get(i).getAmount()+" compressing to "+values.get(i).getType()+", "+values.get(i).getAmount());
            compressables.put(keys.get(i), values.get(i));
        }
    }

    public static void saveCompressables() {
        List<ItemStack> compressablesKeySet = new ArrayList<>(compressables.keySet());
        List<ItemStack> compressablesValueSet = new ArrayList<>(compressables.values());
        compConf.set("keySet", compressablesKeySet.toArray());
        compConf.set("valueSet", compressablesValueSet.toArray());
        try {
            compConf.save(new File(Yesssirbox.getPlugin(Yesssirbox.class).getDataFolder() + File.separator + "compressables.yml"));
        }
        catch (IllegalArgumentException e) {
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().severe("compressables.yml file does not exist - cannot save compressables to config. Data will be lost.");
        }
        catch (IOException e) {
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().warning("IOException whilst saving compressables to file. Data may be lost.");
        }
        Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info("Compressables were saved to file successfully.");
    }

    public static void loadBlockedWords() {
        // TODO load blocked words from conf
        if (blockedWordsConf.getStringList("blockedWords").isEmpty()) {
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().warning("blockedWords.yml is empty - language filter will be inoperable");
            return;
        }
        blockedWords = blockedWordsConf.getStringList("blockedWords");
    }

    public static List<String> getBlockedWords() {
        return blockedWords;
    }

    public static void reloadAllConfigs() {
        File compressorFile = new File(Yesssirbox.getPlugin(Yesssirbox.class).getDataFolder()+File.separator+"compressables.yml");
        if (!compressorFile.exists()) {
            try {
                if (compressorFile.createNewFile()) {
                    Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info("Successfully created compressables.yml file");
                }
            }
            catch (IOException e) {
                Yesssirbox.getPlugin(Yesssirbox.class).getLogger().warning("Could not create compressables.yml file - please do it manually");
            }
        }
        compConf = YamlConfiguration.loadConfiguration(compressorFile);
        File blockedWordsFile = new File(Yesssirbox.getPlugin(Yesssirbox.class).getDataFolder()+File.separator+"blockedWords.yml");
        if (!blockedWordsFile.exists()) {
            try {
                if (blockedWordsFile.createNewFile()) {
                    Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info("Successfully created blockedWords.yml file");
                }
            }
            catch (IOException e) {
                Yesssirbox.getPlugin(Yesssirbox.class).getLogger().warning("Could not create blockedWords.yml file - please do it manually");
            }
        }
        blockedWordsConf = YamlConfiguration.loadConfiguration(blockedWordsFile);
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public static void refreshBlockValues() {
        List<String> blockValueData = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("blockValues");
        if (!blockValueData.isEmpty()) {
            for (String i : blockValueData) {
                blockValues.put(Material.valueOf(i.split(":")[0]), Double.parseDouble(i.split(":")[1]));
            }
        }
    }

    public static void setDailies() {
        List<String> dailies = Yesssirbox.getPlugin(Yesssirbox.class).getConfig().getStringList("dailies");
        if (dailies.isEmpty()) return;
        for (String daily : dailies) {
            getDailies().put(UUID.fromString(daily.split(":")[0]), Long.parseLong(daily.split(":")[1]));
        }
    }

    private void saveDailies() {
        List<String> dailyList = new ArrayList<>();
        for (UUID uuid : dailies.keySet()) {
            dailyList.add(uuid.toString()+":"+dailies.get(uuid));
        }
        getConfig().set("dailies", dailyList);
    }

    public static HashMap<UUID, Long> getDailies() {
        return dailies;
    }

    public static void updateActionBar(Player player, double value) {
        String previous = actionBars.get(player);
        String toSend;
        if (previous != null) {
            double oldValue = Double.parseDouble(previous.split("&a\\$")[1].split(" ")[0]);
            if (String.valueOf(oldValue).equals(String.valueOf(value)) && (System.currentTimeMillis() - Events.lastBlockBroken.get(player)) < 10000) {
                try {
                    toSend = "&a$" + value + " x" + (Integer.parseInt(previous.split(" x")[1])+1);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    toSend = "&a$" + value + " x2";
                }
            }
            else {
                toSend = "&a$"+value;
            }
        }
        else {
            toSend = "&a$"+value;
        }
        TextComponent component = Component.text(ChatColor.translateAlternateColorCodes('&', toSend));
        player.sendActionBar(component);
        actionBars.put(player, toSend);
    }

    public static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
