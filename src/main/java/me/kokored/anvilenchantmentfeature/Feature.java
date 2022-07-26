package me.kokored.anvilenchantmentfeature;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class Feature implements Listener {

    Plugin plugin = AnvilEnchantmentFeature.getPlugin(AnvilEnchantmentFeature.class);
    Map<Player, Boolean> logMap = new HashMap<>();

    public Feature() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private Map<Enchantment, Integer> getCanEnchant(ItemStack book, ItemStack target) {
        Map<Enchantment, Integer> map = new HashMap<>();

        EnchantmentStorageMeta book_meta = (EnchantmentStorageMeta) book.getItemMeta();

        for (Map.Entry<Enchantment, Integer> entry : book_meta.getStoredEnchants().entrySet()) {
            if (entry.getKey().canEnchantItem(target)) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

        return map;
    }

    @EventHandler
    public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
        ItemStack firstItem = event.getInventory().getItem(0);
        ItemStack secondItem = event.getInventory().getItem(1);
        if (firstItem == null || secondItem == null)
            return;
        if (firstItem.getType() == Material.AIR || secondItem.getType() == Material.AIR)
            return;
        if (firstItem.getType() == Material.ENCHANTED_BOOK)
            return;
        if (!(secondItem.getType() == Material.ENCHANTED_BOOK))
            return;
        if (firstItem.getAmount() != 1)
            return;

        ItemStack item = new ItemStack(firstItem.getType());
        ItemMeta meta = firstItem.getItemMeta();
        for (Map.Entry<Enchantment, Integer> entry : this.getCanEnchant(secondItem, firstItem).entrySet()) {
            Enchantment enchant = entry.getKey();

            int resultLvl = meta.getEnchantLevel(enchant) + entry.getValue();
            if (resultLvl > plugin.getConfig().getInt("enchants." + enchant.getKey().asString() + ".maxlvl")) {
                resultLvl = plugin.getConfig().getInt("enchants." + enchant.getKey().asString() + ".maxlvl");
            }

            plugin.getLogger().info(resultLvl + "");

            if (meta.hasConflictingEnchant(enchant)) {
                meta.removeEnchant(enchant);
                meta.addEnchant(enchant, resultLvl, true);
            } else {
                meta.addEnchant(enchant, resultLvl, true);
            }
        }
        item.setItemMeta(meta);

        if (!item.equals(firstItem) || !item.getItemMeta().equals(firstItem.getItemMeta())) {
            event.setResult(item);
        } else {
            ItemStack cannot = new ItemStack(Material.BARRIER);
            ItemMeta cannotMeta = cannot.getItemMeta();
            cannotMeta.displayName(Component.text(ChatColor.RED + "你不能將這個附魔附在這個物品上"));
            cannot.setItemMeta(cannotMeta);

            event.setResult(cannot);
            logMap.put((Player) event.getView().getPlayer(), true);
        }

    }

    @EventHandler
    public void onGetResult(InventoryClickEvent event) {
        ItemStack cannot = new ItemStack(Material.BARRIER);
        ItemMeta cannotMeta = cannot.getItemMeta();
        cannotMeta.displayName(Component.text(ChatColor.RED + "你不能將這個附魔附在這個物品上"));
        cannot.setItemMeta(cannotMeta);

        InventoryType type = event.getInventory().getType();
        if (type == InventoryType.ANVIL) {
            if (event.getSlot() == 2) {
                if (event.getCurrentItem().equals(cannot) || event.getCursor().equals(cannot)) {
                    event.setCancelled(true);
                    return;
                }
                if (logMap.containsKey(event.getWhoClicked())) {
                    event.setCancelled(true);
                }
            } else if (logMap.containsKey((Player) event.getView().getPlayer())) {
                logMap.remove((Player) event.getView().getPlayer());
            }
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        InventoryType type = event.getInventory().getType();
        if (type == InventoryType.ANVIL) {
            if (logMap.containsKey(event.getView().getPlayer())) {
                logMap.remove((Player) event.getView().getPlayer());
            }
        }
    }

}
