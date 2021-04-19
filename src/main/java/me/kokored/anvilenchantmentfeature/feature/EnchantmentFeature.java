package me.kokored.anvilenchantmentfeature.feature;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentFeature {

    public static Map<Enchantment, Integer> getCanEnchant(ItemStack book, ItemStack target) {
        Map<Enchantment, Integer> map = new HashMap<>();

        EnchantmentStorageMeta book_meta = (EnchantmentStorageMeta) book.getItemMeta();

        for (Map.Entry<Enchantment, Integer> entry : book_meta.getStoredEnchants().entrySet()) {
            if (entry.getKey().canEnchantItem(target)) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

        return map;
    }

}
