package me.trouper.yessirbox.data;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.yessirbox.YessirBox;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BanStorage implements JsonSerializable<BanStorage>, Global {

    private final Map<UUID,Integer> storage = new HashMap<>();
    @Override
    public File getFile() {
        return new File("plugins/YessirBox/banstorage.json");
    }
    public Map<UUID,Integer> getStorage() {
        return storage;
    }
    public void incrementCount(OfflinePlayer p) {
        UUID uuid = p.getUniqueId();
        if (storage.containsKey(uuid)) {
            storage.put(uuid,storage.get(uuid) + 1);
            return;
        }
        storage.put(uuid,1);
        YessirBox.banStorage.save();
    }
}
