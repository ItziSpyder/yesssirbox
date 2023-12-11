package me.trouper.yessirbox;

import io.github.itzispyder.pdk.PDK;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.yessirbox.commands.*;
import me.trouper.yessirbox.data.BountyStorage;
import me.trouper.yessirbox.data.Config;
import me.trouper.yessirbox.events.AutoCompressorEvent;
import me.trouper.yessirbox.events.BountyEvent;
import me.trouper.yessirbox.system.CompressionRegistry;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class YessirBox extends JavaPlugin {
    public static Config config = JsonSerializable.load("plugins/YessirBox/config.json", Config.class, new Config());
    public static BountyStorage bounties = JsonSerializable.load("plugins/YessirBox/bounties.json", BountyStorage.class, new BountyStorage());
    private static YessirBox instance;
    public static final Logger log = Bukkit.getLogger();
    public static final CompressionRegistry compressionRegistry = JsonSerializable.load("plugins/YessirBox/compressions.json", CompressionRegistry.class, new CompressionRegistry());
    public static Economy econ;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        reloadKonfig();
    }

    public void init() {
        PDK.init(this);
        instance = this;
        config.save();
        initEvents();
        initEconomy();
        initCommands();
    }

    public static void reloadKonfig() {
        config = JsonSerializable.load("plugins/YessirBox/config.json", Config.class, new Config());
    }

    public void initEvents() {
        new BountyEvent().register();
        new AutoCompressorEvent().register();
    }

    public void initCommands() {
        new AdminCommand().register();
        new CompressCommand().register();
        new AutoCompressorCommand().register();
        new BountyCommand().register();
        new DiscordCommand().register();
        new VoteCommand().register();
    }
    private void initEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }
    public static YessirBox getInstance() {
        return instance;
    }
}
