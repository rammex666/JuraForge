package fr.rammex.juraforge.craft.events;

import fr.rammex.juraforge.craft.InventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

public class CraftRuneTableListener implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.SMITHING_TABLE) {
            ItemStack itemInHand = event.getItemInHand();
            if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
                String displayName = itemInHand.getItemMeta().getDisplayName();
                if (ChatColor.translateAlternateColorCodes('&', "&c&lForge Runic").equals(displayName)) {

                    double x = event.getBlockPlaced().getLocation().getX();
                    double y = event.getBlockPlaced().getLocation().getY() - 1;
                    double z = event.getBlockPlaced().getLocation().getZ();
                    Location loc = new Location(event.getBlockPlaced().getWorld(), x, y, z);
                    placeArmorStand(loc);

                    loc.getWorld().strikeLightning(loc);


                    Bukkit.getServer().broadcastMessage("§c§lUne Forge Runic est §napparue§r§c§l dans le monde !");
                }
            }
        }
    }

    @EventHandler
    public void onBlockRemoved(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SMITHING_TABLE) {
            var blockLocation = event.getBlock().getLocation();
            BoundingBox boundingBox = BoundingBox.of(blockLocation, 1, 2, 1);

            var nearbyEntities = blockLocation.getWorld().getNearbyEntities(boundingBox);
            for (var entity : nearbyEntities) {
                if (entity instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (ChatColor.translateAlternateColorCodes('&', "&c&lForge Runic").equals(armorStand.getCustomName())) {
                        armorStand.remove();
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.SMITHING_TABLE) {
                var blockLocation = event.getClickedBlock().getLocation();
                BoundingBox boundingBox = BoundingBox.of(blockLocation, 1, 2, 1);

                var nearbyEntities = blockLocation.getWorld().getNearbyEntities(boundingBox);
                for (var entity : nearbyEntities) {
                    if (entity instanceof ArmorStand) {
                        ArmorStand armorStand = (ArmorStand) entity;
                        if (ChatColor.translateAlternateColorCodes('&',"&c&lForge Runic").equals(armorStand.getCustomName())) {
                            event.getPlayer().sendMessage("ArmorStand 'puf' found above the Smithing Table!");
                            event.setCancelled(true);
                            InventoryBuilder.buildCraftRuneTable(event.getPlayer());
                            return;
                        }
                    }
                }

                event.getPlayer().sendMessage("No ArmorStand named 'puf' found above the Smithing Table.");
            }
        }
    }



    private void placeArmorStand(Location loc) {
        loc.add(0.5, 0, 0.5);
        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
        armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lForge Runic"));
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
    }
}