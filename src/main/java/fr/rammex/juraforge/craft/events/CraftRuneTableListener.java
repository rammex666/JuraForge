package fr.rammex.juraforge.craft.events;

import fr.rammex.juraforge.craft.InventoryBuilder;
import fr.rammex.juraforge.craft.PaternChecker;
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
                            event.setCancelled(true);
                            InventoryBuilder.buildCraftRuneTable(event.getPlayer());
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        List<Integer> craftSlots = List.of(10, 11, 12, 19, 20, 21, 28, 29, 30);
        String inventoryName = event.getView().getTitle();
        if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', "&c&lForge Runic"))) {

            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null || currentItem.getType() == Material.AIR) {
                return;
            }

            int restrictedSlot = 25;

            if (currentItem.getType() == Material.YELLOW_STAINED_GLASS_PANE || currentItem.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                event.setCancelled(true);
            } else {
                if (event.getSlot() == restrictedSlot && event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
                    event.setCancelled(true);
                } else if (event.getSlot() == restrictedSlot && currentItem.getType() != Material.AIR) {
                    for (int slot : craftSlots) {
                        event.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
                } else {
                    for (int slot : craftSlots) {
                        if (event.getSlot() == slot) {
                            ItemStack slot1 = event.getInventory().getItem(10) != null ? event.getInventory().getItem(10) : new ItemStack(Material.AIR);
                            ItemStack slot2 = event.getInventory().getItem(11) != null ? event.getInventory().getItem(11) : new ItemStack(Material.AIR);
                            ItemStack slot3 = event.getInventory().getItem(12) != null ? event.getInventory().getItem(12) : new ItemStack(Material.AIR);
                            ItemStack slot4 = event.getInventory().getItem(19) != null ? event.getInventory().getItem(19) : new ItemStack(Material.AIR);
                            ItemStack slot5 = event.getInventory().getItem(20) != null ? event.getInventory().getItem(20) : new ItemStack(Material.AIR);
                            ItemStack slot6 = event.getInventory().getItem(21) != null ? event.getInventory().getItem(21) : new ItemStack(Material.AIR);
                            ItemStack slot7 = event.getInventory().getItem(28) != null ? event.getInventory().getItem(28) : new ItemStack(Material.AIR);
                            ItemStack slot8 = event.getInventory().getItem(29) != null ? event.getInventory().getItem(29) : new ItemStack(Material.AIR);
                            ItemStack slot9 = event.getInventory().getItem(30) != null ? event.getInventory().getItem(30) : new ItemStack(Material.AIR);

                            if (slot1.getType() != Material.AIR && slot2.getType() != Material.AIR && slot3.getType() != Material.AIR &&
                                    slot4.getType() != Material.AIR && slot5.getType() != Material.AIR && slot6.getType() != Material.AIR &&
                                    slot7.getType() != Material.AIR && slot8.getType() != Material.AIR && slot9.getType() != Material.AIR) {
                                event.getInventory().setItem(restrictedSlot, PaternChecker.checkPatern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9));
                            }
                        }
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