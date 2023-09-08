package xyz.skaerf.yesssirbox;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Compressable {

    private static HashMap<ItemStack, ItemStack> stupidTranslations = new HashMap<>();

    public static boolean isCompressable(ItemStack stack) {
        if (Yesssirbox.getCompressables().get(stack) != null) {
            return true;
        }
        if (Yesssirbox.getCompressables().get(new ItemStack(stack.getType(), 1)) != null) {
            return true;
        }
        for (ItemStack req : Yesssirbox.getCompressables().keySet()) {
            if (stack.getType().equals(req.getType()) && stack.getAmount()-req.getAmount() >= 0) {
                if (stack.getEnchantments().equals(req.getEnchantments()) && stack.getItemMeta().equals(req.getItemMeta())) {
                    stupidTranslations.put(stack, req);
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack compress(ItemStack stack) {
        Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(stack.getType()+", "+stack.getAmount());
        int resultAmount = 0;
        int requiredForEach = 0;
        boolean setNames = false;
        ItemStack compressTo = Yesssirbox.getCompressables().get(stack);
        if (compressTo == null) {
            compressTo = Yesssirbox.getCompressables().get(new ItemStack(stack.getType(), 1));
            if (compressTo == null) {
                if (stupidTranslations.get(stack) != null) {
                    Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info("compressTo was null - using a stupidTranslation");
                    compressTo = Yesssirbox.getCompressables().get(stupidTranslations.get(stack));
                    if (compressTo == null) return null;
                    requiredForEach = stupidTranslations.get(stack).getAmount();
                }
                else {
                    return null;
                }
            }
            else {
                // it do this
                Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(String.valueOf(compressTo.getAmount()));
                Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(String.valueOf(stack.getAmount()));
                compressTo.setAmount(compressTo.getAmount() * stack.getAmount());
            }
        }

        if (compressTo.getItemMeta().hasDisplayName()) setNames = true;
        if (requiredForEach == 0) requiredForEach = stack.getAmount();
        if (stack.getAmount() >= compressTo.getAmount()) {
            // loop required here - for every REQ amount of item to compress, remove from stack amount
            while (stack.getAmount() >= requiredForEach) {
                stack.setAmount(stack.getAmount()-requiredForEach);
                resultAmount = resultAmount + compressTo.getAmount();
            }
            ItemStack compressedMaterial = new ItemStack(compressTo.getType(), resultAmount);
            compressedMaterial.addUnsafeEnchantments(compressTo.getEnchantments());
            ItemMeta compressedMaterialMeta = compressedMaterial.getItemMeta();
            if (setNames) compressedMaterialMeta.displayName(compressTo.displayName());
            compressedMaterial.setItemMeta(compressedMaterialMeta);
            return compressedMaterial;
        }
        else {
            // while the amount in stack is greater than zero, add to resultAmount each loop the compressTo amount
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(String.valueOf(resultAmount));
            resultAmount = compressTo.getAmount();
            Yesssirbox.getPlugin(Yesssirbox.class).getLogger().info(String.valueOf(resultAmount));
            ItemStack compressedMaterial = new ItemStack(compressTo.getType(), resultAmount);
            compressedMaterial.addUnsafeEnchantments(compressTo.getEnchantments());
            ItemMeta compressedMaterialMeta = compressedMaterial.getItemMeta();
            if (setNames) compressedMaterialMeta.displayName(compressTo.displayName());
            compressedMaterial.setItemMeta(compressedMaterialMeta);
            return compressedMaterial;
        }
    }
}
