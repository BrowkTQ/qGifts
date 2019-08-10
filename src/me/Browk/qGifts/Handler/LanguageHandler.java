package me.Browk.qGifts.Handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class LanguageHandler {

    static LanguageHandler instance = new LanguageHandler();
    FileConfiguration config;
    File cfile;

    public static LanguageHandler getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "language.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("qGifts by Browk_.\n");
        config.addDefault("Command.Must be player", "&7&l(&3&l!&7&l) &cYou must be player!");
        config.addDefault("Command.No permission", "&7&l(&3&l!&7&l) &cYou have no permission!");
        config.addDefault("Command.List heads", "&7&l(&3&l!&7&l) &fHeads:");
        config.addDefault("Command.List locations", "&7&l(&3&l!&7&l) &fLocations:");
        config.addDefault("Command.List Separator", "&f, ");
        config.addDefault("Command.List Item Color", "&b");
        config.addDefault("Command.Head does not exist", "&7&l(&3&l!&7&l) &cThe specified ID does not match any head.");
        config.addDefault("Command.Head added", "&7&l(&3&l!&7&l) &fThe head has been added.");
        config.addDefault("Command.Head removed", "&7&l(&3&l!&7&l) &fThe head has been removed.");
        config.addDefault("Command.Head not found", "&7&l(&3&l!&7&l) &cThe specified head has never been added.");
        config.addDefault("Command.Location already exists", "&7&l(&3&l!&7&l) &cThis location is already saved.");
        config.addDefault("Command.Name not available", "&7&l(&3&l!&7&l) &cLocation name is already used.");
        config.addDefault("Command.Location added", "&7&l(&3&l!&7&l) &fThe location has been added.");
        config.addDefault("Command.Location removed", "&7&l(&3&l!&7&l) &fThe location has been removed.");
        config.addDefault("Command.Location not found", "&7&l(&3&l!&7&l) &cThe specified location has never been added.");
        config.addDefault("Command.Item already exists", "&7&l(&3&l!&7&l) &cThis item is already saved.");
        config.addDefault("Command.Item added", "&7&l(&3&l!&7&l) &fThe item has been added.");
        config.addDefault("Command.Item removed", "&7&l(&3&l!&7&l) &fThe item has been removed.");
        config.addDefault("Command.Item not found", "&7&l(&3&l!&7&l) &cThe specified item has never been added.");
        config.addDefault("Command.Heads spawned", "&7&l(&3&l!&7&l) &fAll heads has been spawned.");
        config.addDefault("Command.Too high number", "&7&l(&3&l!&7&l) &cThe specified number must be lower than total locations number.");
        config.addDefault("Command.Teleported", "&7&l(&3&l!&7&l) &fYou have been teleported to specified location.");
        config.addDefault("Command.Configuration reset", "&7&l(&3&l!&7&l) &fThe &c%file_name%&f file has been reset to default.");
        config.addDefault("Event.Gift found", "&7&l(&3&l!&7&l) &fYou found a gift!");
        config.addDefault("Event.Gifts spawned", "&7&l(&3&l!&7&l) &fYou can find &b&l%number% &fgift in spawn!");
        config.options().copyDefaults(true);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Command.Must be player", "&7&l(&3&l!&7&l) &cYou must be player!");
        config.set("Command.No permission", "&7&l(&3&l!&7&l) &cYou have no permission!");
        config.set("Command.List heads", "&7&l(&3&l!&7&l) &fHeads:");
        config.set("Command.List locations", "&7&l(&3&l!&7&l) &fLocations:");
        config.set("Command.List Separator", "&f, ");
        config.set("Command.List Item Color", "&b");
        config.set("Command.Head does not exist", "&7&l(&3&l!&7&l) &cThe specified ID does not match any head.");
        config.set("Command.Head added", "&7&l(&3&l!&7&l) &fThe head has been added.");
        config.set("Command.Head removed", "&7&l(&3&l!&7&l) &fThe head has been removed.");
        config.set("Command.Head not found", "&7&l(&3&l!&7&l) &cThe specified head has never been added.");
        config.set("Command.Location already exists", "&7&l(&3&l!&7&l) &cThis location is already saved.");
        config.set("Command.Name not available", "&7&l(&3&l!&7&l) &cLocation name is already used.");
        config.set("Command.Location added", "&7&l(&3&l!&7&l) &fThe location has been added.");
        config.set("Command.Location removed", "&7&l(&3&l!&7&l) &fThe location has been removed.");
        config.set("Command.Location not found", "&7&l(&3&l!&7&l) &cThe specified location has never been added.");
        config.set("Command.Item already exists", "&7&l(&3&l!&7&l) &cThis item is already saved.");
        config.set("Command.Item added", "&7&l(&3&l!&7&l) &fThe item has been added.");
        config.set("Command.Item removed", "&7&l(&3&l!&7&l) &fThe item has been removed.");
        config.set("Command.Item not found", "&7&l(&3&l!&7&l) &cThe specified item has never been added.");
        config.set("Command.Heads spawned", "&7&l(&3&l!&7&l) &fAll heads has been spawned.");
        config.set("Command.Too high number", "&7&l(&3&l!&7&l) &cThe specified number must be lower than total locations number.");
        config.set("Command.Teleported", "&7&l(&3&l!&7&l) &fYou have been teleported to specified location.");
        config.set("Command.Configuration reset", "&7&l(&3&l!&7&l) &fThe &c%file_name%&f file has been reset to default.");
        config.set("Event.Gift found", "&7&l(&3&l!&7&l) &fYou found a gift!");
        config.set("Event.Gifts spawned", "&7&l(&3&l!&7&l) &fYou can find &b&l%number% &fgift in spawn!");
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'language.yml' couldn't be saved!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}
