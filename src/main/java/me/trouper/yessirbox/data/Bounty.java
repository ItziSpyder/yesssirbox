package me.trouper.yessirbox.data;

import me.trouper.yessirbox.utils.TimeStamp;

import java.util.UUID;

public record Bounty(UUID setter, UUID target, int amount, TimeStamp created, long duration) {
    public String getInfo() {
        return "Setter: " + setter +
                "\nTarget: " + target +
                "\nWorth: " + amount +
                "\nCreated on: " + created +
                "\nDuration: " + duration;
    }
}
