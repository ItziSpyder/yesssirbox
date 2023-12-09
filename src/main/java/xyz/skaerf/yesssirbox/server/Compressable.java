package xyz.skaerf.yesssirbox.server;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.skaerf.yesssirbox.Yesssirbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Compressable {

    public static boolean isCompressable(ItemStack stack) {
        if (Yesssirbox.getPreCompressables().get(stack) != null) {
            return true;
        }
        if (Yesssirbox.getCompressable(stack).isEmpty()) {
            return true;
        }
        if (Yesssirbox.getCompressable(stack).size() > 1) {
            return true;
        }
        return false;
    }

    public static List<ItemStack> compress(ItemStack stack) {
        Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(stack.getType()+", "+stack.getAmount());
        ItemStack compressTo = Yesssirbox.getPreCompressables().get(new ItemStack(stack.getType()));
        if (compressTo != null) {
            // is preCompress
            System.out.println("preComp");
            ItemStack returnable = new ItemStack(compressTo.getType());
            while (stack.getAmount() >= compressTo.getAmount()) {
                stack.setAmount(stack.getAmount() - compressTo.getAmount());
                returnable.setAmount(returnable.getAmount() + compressTo.getAmount());
            }
            return new ArrayList<>(Arrays.asList(stack, returnable));
        }
        List<ItemStack> compressables = Yesssirbox.getCompressable(stack); // in an ideal world this will have three values - 0 = stack, 1 = from, 2 = to
        System.out.println(compressables);
        if (compressables.size() == 1) return null;
        if (compressables.size() == 2) {
            System.out.println("size2");
            // directly compressable stack
            System.out.println(compressables.get(1)); // TODO why the goddamn fuck is this returning an ItemStack of air
            return new ArrayList<>(Arrays.asList(stack, compressables.get(1)));
        }
        if (compressables.size() == 3) {
            System.out.println("size3");
            // stack is compressable, but is larger than required (can be recurred based on returned values)
            ItemStack returnable = new ItemStack(compressables.get(1).getType());
            ItemStack undersizedStack = compressables.get(2);
            while (stack.getAmount() >= undersizedStack.getAmount()) {
                returnable.setAmount(returnable.getAmount()+undersizedStack.getAmount());
                stack.setAmount(stack.getAmount()-undersizedStack.getAmount());
            }
            return new ArrayList<>(Arrays.asList(stack, returnable));
        }
        ItemStack returnable = new ItemStack(compressTo.getType()); // TODO Why god
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
