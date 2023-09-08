package xyz.skaerf.yesssirbox;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Compressable {

    public static boolean isCompressable(ItemStack stack) {
        if (Yesssirbox.getCompressable(stack).isEmpty()) {
            return true;
        }
        if (Yesssirbox.getPreCompressables().get(stack) != null) {
            return true;
        }
        return false;
    }

    public static List<ItemStack> compress(ItemStack stack) {
        Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(stack.getType()+", "+stack.getAmount());
        ItemStack compressTo = Yesssirbox.getPreCompressables().get(new ItemStack(stack.getType()));
        if (compressTo != null) {
            // is preCompress
            ItemStack returnable = new ItemStack(compressTo.getType());
            while (stack.getAmount() >= compressTo.getAmount()) {
                stack.setAmount(stack.getAmount() - compressTo.getAmount());
                returnable.setAmount(returnable.getAmount() + compressTo.getAmount());
            }
            return new ArrayList<>(Arrays.asList(stack, returnable));
        }
        List<ItemStack> compressables = Yesssirbox.getCompressable(stack); // in an ideal world this will have three values - 0 = stack, 1 = from, 2 = to
        if (compressables.size() == 1) return null;
        if (compressables.size() == 2) {
            // directly compressable stack
        }
        if (compressables.size() == 3) {
            // stack is compressable, but is larger than required (can be recurred based on returned values)
        }
        ItemStack returnable = new ItemStack(compressTo.getType());
        if (!compressTo.getEnchantments().isEmpty()) returnable.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        if (compressTo.getItemMeta() != null) {
            ItemMeta returnableMeta = returnable.getItemMeta();
            returnableMeta.displayName(compressTo.displayName());
            returnable.setItemMeta(returnableMeta);
        }
        while (stack.getAmount() >= compressTo.getAmount()) {
            stack.setAmount(stack.getAmount() - compressTo.getAmount());
            returnable.setAmount(returnable.getAmount()+compressTo.getAmount());
        }
        return new ArrayList<>(Arrays.asList(stack, returnable));
    }
}
