package me.Browk.qGifts.Listeners;

import me.Browk.qGifts.Main;
import me.Browk.qGifts.Utils.MessageUtils;
import me.Browk.qGifts.Utils.Utils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener, MessageUtils, Utils {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if(!Main.getInstance().getLocations().containsValue(block.getLocation()) || block.getType() != Material.SKULL) {
            return;
        }
        block.setType(Material.AIR);
        Player player = event.getPlayer();
        player.getInventory().addItem(getRandomItem());
        block.getWorld().playEffect(block.getLocation().add(0.9, 0.2, 0.1), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.9, 0.5, 0.8), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.9, 0.8, 0.4), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.1, 0.2, 0.1), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.1, 0.5, 0.8), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.1, 0.8, 0.4), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.1, 0.2, 0.9), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.8, 0.5, 0.9), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.4, 0.8, 0.9), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.1, 0.2, 0.1), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.8, 0.5, 0.1), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        block.getWorld().playEffect(block.getLocation().add(0.4, 0.8, 0.1), Effect.HAPPY_VILLAGER, Integer.MAX_VALUE);
        player.sendMessage(getMessage("Event.Gift found"));
    }

}
