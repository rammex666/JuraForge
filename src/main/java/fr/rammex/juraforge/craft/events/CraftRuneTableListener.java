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
    public void onInventoryClick(InventoryClickEvent event) {
        List<Integer> craftSlots = new ArrayList<>();
        String inventoryName = event.getView().getTitle();
        if (inventoryName.equals(ChatColor.translateAlternateColorCodes('&', "&c&lForge Runic"))) {

            craftSlots.add(10);
            craftSlots.add(11);
            craftSlots.add(12);
            craftSlots.add(19);
            craftSlots.add(20);
            craftSlots.add(21);
            craftSlots.add(28);
            craftSlots.add(29);
            craftSlots.add(30);
            int restrictedSlot = 25;

            if (event.getCurrentItem() != null && (event.getCurrentItem().getType() == Material.YELLOW_STAINED_GLASS_PANE || event.getCurrentItem().getType() == Material.ORANGE_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
            } else {
                if (event.getSlot() == restrictedSlot && event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
                    event.setCancelled(true);
                } else if (event.getSlot() == restrictedSlot && event.getCurrentItem().getType() != Material.AIR) {
                    for (int slot : craftSlots) {
                        event.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
                } else {
                    for (int slot : craftSlots) {
                        if (event.getSlot() == slot) {
                            event.getInventory().setItem(restrictedSlot, PaternChecker.checkPatern(event.getInventory().getItem(10),
                                    event.getInventory().getItem(11),
                                    event.getInventory().getItem(12),
                                    event.getInventory().getItem(19),
                                    event.getInventory().getItem(20),
                                    event.getInventory().getItem(21),
                                    event.getInventory().getItem(28),
                                    event.getInventory().getItem(29),
                                    event.getInventory().getItem(30)));
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