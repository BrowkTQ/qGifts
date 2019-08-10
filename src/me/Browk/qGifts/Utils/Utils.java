package me.Browk.qGifts.Utils;

import me.Browk.qGifts.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public interface Utils {

    default String locationToString(Location l){
        return l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l.getWorld().getName();
    }

    default Location stringToLocation(String s){
        String[] split = s.split(",");
        return new Location(Bukkit.getWorld(split[3]), Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

    default ItemStack getRandomItem() {
        ArrayList<ItemStack> items = Main.getInstance().getItems();
        return items.get(new Random().nextInt(items.size()));
    }

    @SuppressWarnings("deprecation")
    default String itemStackSerialization(ItemStack is) {
        StringBuilder serializedItemStack = new StringBuilder();
        final String isType = String.valueOf(is.getType().getId());
        serializedItemStack.append("t@").append(isType);
        if (is.getDurability() != 0) {
            String isDurability = String.valueOf(is.getDurability());
            serializedItemStack.append(":d@").append(isDurability);
        }
        if (is.getAmount() != 1) {
            final String isAmount = String.valueOf(is.getAmount());
            serializedItemStack.append(":a@").append(isAmount);
        }
        final Map<Enchantment, Integer> isEnch = is.getEnchantments();
        if (isEnch.size() > 0) {
            StringBuilder serializedItemStackBuilder = new StringBuilder(serializedItemStack.toString());
            for (final Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
                serializedItemStackBuilder.append(":e@").append(ench.getKey().getId()).append("@").append(ench.getValue());
            }
            serializedItemStack = new StringBuilder(serializedItemStackBuilder.toString());
        }
        if (is.getItemMeta().getDisplayName() != null) {
            final String[] itemDisplayName = is.getItemMeta().getDisplayName().split(" ");
            serializedItemStack.append(":n@");
            for (String s : itemDisplayName) {
                serializedItemStack.append(s).append("=");
            }
        }
        return serializedItemStack.toString();
    }

    @SuppressWarnings("deprecation")
    default ItemStack itemStackDeserialization(String string) {
        ItemStack is = null;
        boolean createdItemStack = false;
        final String[] serializedItemStack = string.split(":");
        String[] array;
        for (int length = (array = serializedItemStack).length, j = 0; j < length; ++j) {
            final String itemInfo = array[j];
            final String[] itemAttribute = itemInfo.split("@");
            if (itemAttribute[0].equals("t")) {
                is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1])));
                createdItemStack = true;
            } else if (itemAttribute[0].equals("d") && createdItemStack) {
                is.setDurability(Short.valueOf(itemAttribute[1]));
            } else if (itemAttribute[0].equals("a") && createdItemStack) {
                is.setAmount(Integer.valueOf(itemAttribute[1]));
            } else if (itemAttribute[0].equals("e") && createdItemStack) {
                is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
            } else if (itemAttribute[0].equals("n") && createdItemStack) {
                final ItemMeta meta = is.getItemMeta();
                final String[] displayName = itemAttribute[1].split("=");
                StringBuilder finalName = new StringBuilder();
                for (int m = 0; m < displayName.length; ++m) {
                    if (m == displayName.length - 1) {
                        finalName.append(displayName[m]);
                    } else {
                        finalName.append(displayName[m]).append(" ");
                    }
                }
                meta.setDisplayName(finalName.toString());
                is.setItemMeta(meta);
            }
        }
        return is;
    }

}
