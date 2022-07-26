package me.kokored.anvilenchantmentfeature;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilEnchantmentFeature extends JavaPlugin {

    @Override
    public void onEnable() {
        for (Enchantment enchant : Enchantment.values()) {
            getConfig().addDefault("enchants." + enchant.getKey().asString() + ".maxlvl", enchant.getMaxLevel());
        }
        getConfig().options().copyDefaults(true);
        saveConfig();

        new Feature();
        
    }

    @Override
    public void onDisable() {
    }
}
