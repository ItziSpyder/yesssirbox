package me.trouper.yessirbox.system;

import io.github.itzispyder.pdk.Global;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import io.github.itzispyder.pdk.utils.misc.Pair;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompressionRegistry implements JsonSerializable<CompressionRegistry>, Global {

    private final List<CompressInstruction> instructions;

    @Override
    public File getFile() {
        return new File("plugins/YessirBox/compressions.json");
    }

    public CompressionRegistry() {
        this.instructions = new ArrayList<>();
    }

    public Pair<Boolean,CompressInstruction> getCompressable(ItemStack i) {
        if (i == null || i.getType().isAir()) {
            return Pair.of(false,null);
        }
        for (CompressInstruction ins : instructions) {
            var c = ins.getCompress();
            if (c.getMaterial() == i.getType()) {
                return Pair.of(c.getNbt().equals(i.getType().name().toLowerCase() + i.getItemMeta().getAsString()), ins);
            }
        }
        return Pair.of(false,null);
    }

    public void removeInstructions(CompressInstruction instruction) {
        instructions.remove(instruction);
    }

    public void registerInstructions(CompressInstruction instruction) {
        if (!instructions.contains(instruction)) {
            instructions.add(instruction);
        }
    }

    public List<CompressInstruction> getInstructions() {
        return instructions;
    }
}
