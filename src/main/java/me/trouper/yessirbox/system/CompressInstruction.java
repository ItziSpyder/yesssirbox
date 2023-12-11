package me.trouper.yessirbox.system;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
public class CompressInstruction {

    private final Compression compress;
    private final Compression to;

    public CompressInstruction(ItemStack compress, ItemStack to) {
        this.compress = new Compression(compress);
        this.to = new Compression(to);
    }

    public Compression getCompress() {
        return compress;
    }

    public Compression getTo() {
        return to;
    }

    public String serializeJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return serializeJson();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CompressInstruction instructions)) {
            return false;
        }
        return this.serializeJson().equals(instructions.serializeJson());
    }

    public static class Compression {
        private final int count;
        private final String nbt;
        private final Material material;

        public Compression(ItemStack stack) {
            this.count = stack.getAmount();
            this.material = stack.getType();
            this.nbt = stack.getType().name().toLowerCase().concat(stack.getItemMeta().getAsString());
        }

        public ItemStack load() {
            ItemStack item = Bukkit.getItemFactory().createItemStack(nbt);
            item.setAmount(count);
            return item;
        }

        public Material getMaterial() {
            return material;
        }

        public int getCount() {
            return count;
        }

        public String getNbt() {
            return nbt;
        }
    }
}