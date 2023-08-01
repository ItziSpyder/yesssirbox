package xyz.skaerf.yesssirbox.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import xyz.skaerf.yesssirbox.AutoCompressor;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.io.File;

public class ConfigUtil {
    private File file;
    private FileConfiguration config;
    public ConfigUtil(Plugin plugin, String path) {
        this(plugin.getDataFolder().getAbsolutePath() + "/" + path);

    }

    public ConfigUtil (String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void reload(){
        save();
        ConfigUtil autocompressorConfig = new ConfigUtil("autocompressor.yml");
        AutoCompressor.setCompressorItems(autocompressorConfig.getConfig());
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

}
