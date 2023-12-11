package me.trouper.yessirbox.data;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config implements JsonSerializable<Config> {
    public String prefix = "&fYessirBox &8» &7";
    public String discordInvite = "https://discord.gg/jmtPkbWjD7";
    public List<String> voteLinks = new ArrayList<>();
    public BountyConfig bounties = new BountyConfig();

    @Override
    public File getFile() {
        return new File("plugins/YessirBox/config.json");
    }

    public static class BountyConfig {
        public int minWorth = 1;
        public int maxWorth = 1000000;
        public boolean allowDuplicates = false;
        public int minTime = 1;
        public int maxTime = 24;
    }
}
