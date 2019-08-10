package me.Browk.qGifts.Handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConfigHandler {

    static ConfigHandler instance = new ConfigHandler();
    FileConfiguration config;
    File cfile;

    public static ConfigHandler getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("qGifts by Browk_.\n");
        config.addDefault("Heads", Arrays.asList("2486", "3159", "2489", "3157"));
        config.options().copyDefaults(true);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Heads", Arrays.asList("2486", "3159", "2489", "3157"));
        config.set("Locations", null);
        config.set("Items", null);
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'settings.yml' couldn't be saved!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}
