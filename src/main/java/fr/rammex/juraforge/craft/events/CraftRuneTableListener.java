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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        List<Integer> craftSlots = new ArrayList<Integer>();
        String inventoryName = event.getView().getTitle();
        if(inventoryName.equals(ChatColor.translateAlternateColorCodes('&', "&c&lForge Runic"))){
            craftSlots.add(10);
            if(event.getCurrentItem().getType() == Material.YELLOW_STAINED_GLASS_PANE || event.getCurrentItem().getType() == Material.ORANGE_STAINED_GLASS_PANE){
                event.setCancelled(true);
            } else {
                if(event.getSlot() == 25){
                    for(int slot : craftSlots){
                        event.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&',"&c&lForge Runic"))){
            Inventory inv = event.getInventory();
            for(int i = 0; i < inv.getSize(); i++){
                if(inv.getItem(i) != null && inv.getItem(i).getType() != Material.YELLOW_STAINED_GLASS_PANE && inv.getItem(i).getType() != Material.ORANGE_STAINED_GLASS_PANE && i != 25){
                    event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), inv.getItem(i));
                }
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