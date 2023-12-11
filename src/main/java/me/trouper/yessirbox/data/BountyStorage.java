package me.trouper.yessirbox.data;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BountyStorage implements JsonSerializable<BountyStorage> {

    public List<Bounty> storage = new ArrayList<>();
    @Override
    public File getFile() {
        return new File("plugins/YessirBox/bounties.json");
    }

}
