package fr.rammex.juraforge.craft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryBuilder {

    public void buildCraftRuneTable(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, "Rune Crafter");

        ItemStack fill1Item = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        ItemStack fill2Item = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

        ItemMeta fill1Meta = fill1Item.getItemMeta();
        ItemMeta fill2Meta = fill2Item.getItemMeta();
        fill1Meta.setDisplayName(" ");
        fill2Meta.setDisplayName(" ");
        fill1Item.setItemMeta(fill1Meta);
        fill2Item.setItemMeta(fill2Meta);

        for (int i = 0; i < 45; i++) {
            if(i == 10 || i == 11 || i == 12 || i == 19 || i == 20 || i == 21 || i == 28 || i == 29 || i == 30 || i == 25){
                continue;
            }

            if(isEven(i)){
                inventory.setItem(i, fill1Item);
            } else {
                inventory.setItem(i, fill2Item);
            }


        }

    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

}
