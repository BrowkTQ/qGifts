package me.Browk.qGifts.Commands;

import me.Browk.qGifts.Handler.ConfigHandler;
import me.Browk.qGifts.Main;
import me.Browk.qGifts.Utils.MessageUtils;
import me.Browk.qGifts.Utils.Utils;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiftsCommand implements CommandExecutor, MessageUtils, Utils {

    private ConfigHandler config = ConfigHandler.getInstance();
    private Main instance = Main.getInstance();

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            noConsole(commandSender);
            return true;
        }
        Player player = (Player) commandSender;
        if(!player.hasPermission("qgifts.admin")) {
            player.sendMessage(noPermission());
            return true;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("headlist")) {
            if(instance.getHeads().isEmpty()) {
                player.sendMessage(getMessage("Command.List heads") + " §b-");
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder(getMessage("Command.List heads") + " ");
            for(String string : instance.getHeads()) {
                stringBuilder.append(getMessage("Command.List Item Color")).append(string).append(getMessage("Command.List Separator"));
            }
            player.sendMessage(stringBuilder.substring(0, stringBuilder.length() - getMessage("Command.List Separator").length()));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("addhead")) {
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            if(api.getItemHead(args[1]) == null) {
                player.sendMessage(getMessage("Command.Head does not exist"));
                return true;
            }
            if(instance.getHeads().contains(args[1])) {
                return true;
            }
            instance.addHead(args[1]);
            List<String> list = config.getConfig().getStringList("Heads");
            list.add(args[1]);
            config.getConfig().set("Heads", list);
            config.saveConfig();
            player.sendMessage(getMessage("Command.Head added"));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("removehead")) {
            if(!instance.getHeads().contains(args[1])) {
                player.sendMessage(getMessage("Command.Head not found"));
                return true;
            }
            instance.removeHead(args[1]);
            List<String> list = config.getConfig().getStringList("Heads");
            list.remove(args[1]);
            config.getConfig().set("Heads", list);
            config.saveConfig();
            player.sendMessage(getMessage("Command.Head removed"));
            return true;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("locationlist")) {
            if(instance.getLocations().isEmpty()) {
                player.sendMessage(getMessage("Command.List locations") + " §b-");
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder(getMessage("Command.List locations") + " ");
            for(String string : instance.getLocations().keySet()) {
                stringBuilder.append(getMessage("Command.List Item Color")).append(string).append(getMessage("Command.List Separator"));
            }
            player.sendMessage(stringBuilder.substring(0, stringBuilder.length() - getMessage("Command.List Separator").length()));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("addlocation")) {
            if(instance.getLocations().containsValue(player.getLocation())) {
                player.sendMessage(getMessage("Command.Location already exists"));
                return true;
            }
            if(instance.getLocations().containsKey(args[1])) {
                player.sendMessage(getMessage("Command.Name not available"));
                return true;
            }
            instance.addLocation(args[1], player.getLocation());
            config.getConfig().set("Locations." + args[1], locationToString(player.getLocation()));
            config.saveConfig();
            player.sendMessage(getMessage("Command.Location added"));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("removelocation")) {
            if(!instance.getLocations().containsKey(args[1])) {
                player.sendMessage(getMessage("Command.Location not found"));
                return true;
            }
            instance.removeLocation(args[1]);
            config.getConfig().set("Locations." + args[1], null);
            config.saveConfig();
            player.sendMessage(getMessage("Command.Location removed"));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("gotolocation")) {
            if(!instance.getLocations().containsKey(args[1])) {
                player.sendMessage(getMessage("Command.Location not found"));
                return true;
            }
            player.teleport(instance.getLocations().get(args[1]));
            player.sendMessage(getMessage("Command.Teleported"));
            return true;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("addItem")) {
            @SuppressWarnings("deprecation")
            final ItemStack itemStack = player.getItemInHand();
            if(itemStack == null || itemStack.getType() == Material.AIR) {
                return true;
            }
            if(instance.getItems().contains(itemStack)) {
                player.sendMessage(getMessage("Command.Item already exists"));
                return true;
            }
            instance.addItem(itemStack);
            List<String> list = config.getConfig().getStringList("Items");
            list.add(itemStackSerialization(itemStack));
            config.getConfig().set("Items", list);
            config.saveConfig();
            player.sendMessage(getMessage("Command.Item added"));
            return true;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("removeItem")) {
            final ItemStack itemStack = player.getItemInHand();
            if(itemStack == null || itemStack.getType() == Material.AIR) {
                return true;
            }
            if(!instance.getItems().contains(itemStack)) {
                player.sendMessage(getMessage("Command.Item not found"));
                return true;
            }
            instance.removeItem(itemStack);
            List<String> list = config.getConfig().getStringList("Items");
            list.remove(itemStackSerialization(itemStack));
            config.getConfig().set("Items", list);
            config.saveConfig();
            player.sendMessage(getMessage("Command.Item removed"));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("spawnheads")) {
            Integer x = Integer.valueOf(args[1]);
            ArrayList<Location> list = new ArrayList<>(instance.getLocations().values());
            if(list.size() < x) {
                player.sendMessage(getMessage("Command.Too high number"));
                return true;
            }
            Collections.shuffle(list);
            ArrayList<String> heads = instance.getHeads();
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            for(int i = 0; i < x; i++) {
                Block b = list.get(i).getBlock();

                b.setTypeIdAndData(Material.SKULL.getId(), (byte) 1, true);
                api.setBlockSkin(b, heads.get(i%heads.size()));
            }
            player.sendMessage(getMessage("Command.Heads spawned"));
            Bukkit.broadcastMessage(getMessage("Event.Gifts spawned").replace("%number%", args[1]));
            return true;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("reset")) {
            if (args[1].equalsIgnoreCase("config")) {
                config.resetConfig();
                player.sendMessage(getMessage("Command.Configuration reset").replace("%file_name%", "config.yml"));
                return true;
            }
            if (args[1].equalsIgnoreCase("language")) {
                resetAllMessages();
                player.sendMessage(getMessage("Command.Configuration reset").replace("%file_name%", "language.yml"));
                return true;
            }
        }
        player.sendMessage(replace("&8[&3qGifts&8] &7List of &b&l/qgifts &7subcomands:"
                + "\n&8 » &3headList &7- Shows the heads list."
                + "\n&8 » &3addHead <ID> &7- Adds new head."
                + "\n&8 » &3removeHead <ID> &7- Removes the head."
                + "\n&8 » &3locationList &7- Shows the locations list."
                + "\n&8 » &3addLocation <name> &7- Adds new location."
                + "\n&8 » &3removeLocation <name> &7- Removes the location."
                + "\n&8 » &3goToLocation &7- Teleports you to location."
                + "\n&8 » &3addItem &7- Adds the item from your hand."
                + "\n&8 » &3removeItem &7- Removes the item from your hand."
                + "\n&8 » &3spawnHeads <number> &7- Spawns x heads."
                + "\n&8 » &3reset <config|language> &7- Resets requested file."));
        return true;
    }

}
