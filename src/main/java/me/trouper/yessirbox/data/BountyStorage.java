package me.trouper.yessirbox.data;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.utils.TimeStamp;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BountyStorage implements JsonSerializable<BountyStorage> {

    private final List<Bounty> storage;

    @Override
    public File getFile() {
        return new File("plugins/YessirBox/bounties.json");
    }

    public BountyStorage() {
        this.storage = new ArrayList<>();
    }

    public void addBounty(Bounty b) {
        storage.add(b);
    }

    public void removeBounty(Bounty b) {
        storage.remove(b);
    }

    public List<Bounty> getBounties() {
        return storage;
    }

    public List<String> getActiveBountyNames() {
        List<String> out = new ArrayList<>();
        YessirBox.log.info("Attempting to list active bounties...");
        for (Bounty bounty : storage) {
            YessirBox.verbose("Looping through bounty, " + bounty.target());
            if (isExpired(bounty)) {
                YessirBox.verbose("A bounty on " + bounty.target() + " has been found to be expired. Removing.");
                storage.remove(bounty);
            } else {
                out.add(Bukkit.getOfflinePlayer(bounty.target()).getName());
                YessirBox.verbose("A bounty on " + bounty.target() + " has been found and added.");
            }
        }
        YessirBox.verbose("No bounties has been found.");
        if (out.isEmpty()) out.add("No Bounties Found");
        return out;
    }

    public boolean isExpired(Bounty b) {
        long lasted = b.created().secondsBetween(TimeStamp.now());
        long max = b.duration() * 3600;
        YessirBox.verbose("Checking bounty validity: " + b.target() + " It has been around for " + lasted + " seconds, its max time is " + max + " seconds.");
        return lasted >= max;
    }

}
