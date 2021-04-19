package me.kokored.anvilenchantmentfeature;

import me.kokored.anvilenchantmentfeature.feature.AnvilFeature;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilEnchantmentFeature extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        new AnvilFeature();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

}
