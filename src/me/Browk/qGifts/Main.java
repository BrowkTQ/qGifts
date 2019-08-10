package me.Browk.qGifts;

import me.Browk.qGifts.Commands.GiftsCommand;
import me.Browk.qGifts.Handler.ConfigHandler;
import me.Browk.qGifts.Handler.LanguageHandler;
import me.Browk.qGifts.Listeners.InteractEvent;
import me.Browk.qGifts.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin implements Utils {

    private static Main instance;
    private ArrayList<String> heads;
    private HashMap<String, Location> locations;
    private ArrayList<ItemStack> items;
    private String version;

    @Override
    public void onEnable() {
        instance = this;
        heads = new ArrayList<>();
        locations = new HashMap<>();
        items = new ArrayList<>();
        ConfigHandler config = ConfigHandler.getInstance();
        LanguageHandler lang = LanguageHandler.getInstance();
        config.setup(this);
        lang.setup(this);
        String str = getServer().getClass().getPackage().getName();
        this.version = str.substring(str.lastIndexOf('.') + 1);
        if(!config.getConfig().getStringList("Heads").isEmpty() && config.getConfig().getStringList("Heads") != null) {
            for(String id : config.getConfig().getStringList("Heads")) {
                heads.add(id);
            }
        }
        ConfigurationSection cfg = config.getConfig().getConfigurationSection("Locations");
        if(cfg != null) {
            for(String name : cfg.getKeys(false)) {
                System.out.println(cfg.getString(name));
                locations.put(name, stringToLocation(cfg.getString(name)));
            }
        }
        if(!config.getConfig().getStringList("Items").isEmpty() && config.getConfig().getStringList("Heads") != null) {
            for(String string : config.getConfig().getStringList("Items")) {
                items.add(itemStackDeserialization(string));
            }
        }
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getCommand("qgifts").setExecutor(new GiftsCommand());
    }

    public String getVersion() {
        return version;
    }

    public static Main getInstance() {
        return instance;
    }

    public ArrayList<String> getHeads() {
        return heads;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public HashMap<String, Location> getLocations() {
        return locations;
    }

    public void addHead(String id) {
        heads.add(id);
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    public void addLocation(String name, Location location) {
        locations.put(name, location);
    }

    public void removeHead(String id) {
        heads.remove(id);
    }

    public void removeItem(ItemStack item) {
        items.remove(item);
    }

    public void removeLocation(String name) {
        locations.remove(name);
    }

}
