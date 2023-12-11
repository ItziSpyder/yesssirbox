package me.trouper.yessirbox.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.yessirbox.YessirBox;
import me.trouper.yessirbox.system.CompressInstruction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandRegistry(value="compress",permission=@Permission(value="yessirbox.compress", message="You do not have permission to use compress, purchase it with /buy!"),usage="/compress")
public class CompressCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        if (!(sender instanceof Player p)) {
            return;
        }
        info(p, YessirBox.config.prefix + "Compressing your inventory...");
        execute(p);
    }

    public static void execute(Player p) {
        ItemStack[] contents = p.getInventory().getContents();

        for (ItemStack item : contents) {
            var pair = YessirBox.compressionRegistry.getCompressable(item);
            if (!pair.left) {
                continue;
            }
            CompressInstruction ins = pair.right;

            var comp = ins.getCompress();
            int has = item.getAmount();
            int compCount = comp.getCount();

            int remainder = has % compCount;
            int divisible = has - remainder;
            int compressedToGive = divisible/compCount;

            p.getInventory().remove(item);

            ItemStack loaded = ins.getTo().load();
            loaded.setAmount(compressedToGive);
            p.getInventory().addItem(loaded);

            item.setAmount(remainder);
            p.getInventory().addItem(item);
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {

    }
}
