package fr.rammex.juraforge.craft;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PaternChecker {
    public static ItemStack checkPatern(ItemStack slot1, ItemStack slot2, ItemStack slot3,ItemStack slot4, ItemStack slot5, ItemStack slot6,ItemStack slot7, ItemStack slot8, ItemStack slot9){
        if(slot1.getType().equals(Material.AIR) && slot2.getType().equals(Material.AIR) && slot3.getType().equals(Material.AIR) && slot4.getType().equals(Material.AIR) && slot5.getType().equals(Material.AIR) && slot6.getType().equals(Material.AIR) && slot7.getType().equals(Material.AIR) && slot8.getType().equals(Material.AIR) && slot9.getType().equals(Material.AIR)){
            return new ItemStack(Material.AIR);
        }
        return new ItemStack(Material.AIR);
    }
}
