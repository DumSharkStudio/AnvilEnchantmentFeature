/*
 * Copyright (c) 2021. KokoMinecraftPlugins(http://kokominecraftplugins.github.io/)Koko_red7214 版權所有
 * 19/4/2021授權 快樂貓伺服器(The_chosen _cat#2606)使用
 */

package me.kokored.anvilenchantmentfeature;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilEnchantmentFeature extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(
                "\n==========[ AnvilEnchantmentFeature ]=========="  + "\n" +
                        "Author: KokoMinecraftPlugins(Koko_red7214)"      + "\n" +
                        "Site: http://kokominecraftplugins.github.io/"    + "\n" +
                        "==========[ AnvilEnchantmentFeature ]==========" + "\n");

        new Feature();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(
                "\n==========[ AnvilEnchantmentFeature ]=========="  + "\n" +
                        "Author: KokoMinecraftPlugins(Koko_red7214)"      + "\n" +
                        "Site: http://kokominecraftplugins.github.io/"    + "\n" +
                        "==========[ AnvilEnchantmentFeature ]==========" + "\n");
    }
}
