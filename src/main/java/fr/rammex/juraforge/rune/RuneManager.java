package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.rune.Runes;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.rammex.juraforge.utils.ColorUtils.hex;

public class RuneManager {
    public static final Map<String, Runes> runes = new HashMap<>();

    public void registerRune(Runes rune) {
        runes.put(rune.getId(), rune);
    }

    public void applyRune(ItemStack item, Runes rune) {
        if (!canApplyRune(item, rune)) {
            throw new IllegalArgumentException("Rune cannot be applied to this item.");
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }

            boolean runeUpgrade = false;
            for (int i = 0; i < lore.size(); i++) {
                System.out.println("Lore: " + lore.get(i));
                if (lore.get(i).startsWith(hex("&a✦ " + rune.getName()))) {
                    String[] parts = lore.get(i).split(" - ");
                    int currentLevel = Integer.parseInt(parts[1]);
                    if (currentLevel == rune.getLevel()) {
                        runeUpgrade = true;
                        break;
                    } else {
                        return;
                    }
                }
            }

            if (runeUpgrade) {
                upgradeRune(item, rune);
            } else {
                lore.removeIf(line -> line.startsWith(hex("&a✦ ")));
                lore.add(hex("&a✦ " + rune.getName() + " - " + rune.getLevel()));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        } else {
        }
    }

    public void upgradeRune(ItemStack item, Runes rune) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    if (lore.get(i).startsWith(hex("&a✦ " + rune.getName()))) {
                        String[] parts = lore.get(i).split(" - ");
                        int currentLevel = Integer.parseInt(parts[1]);
                        lore.set(i, hex("&a✦ " + rune.getName() + " - " + (currentLevel + 1)));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        return;
                    }
                }
            }
        }
    }

    public boolean canApplyRune(ItemStack item, Runes rune) {
        boolean canApply = rune.getAllowedItems().contains(item.getType().toString());
        return canApply;
    }

    public void removeRune(ItemStack item, Runes rune) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null && lore.remove(hex("&a✦ "+ rune.getName()))) {
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

    public static Runes getRune(String id) {
        return runes.get(id);
    }

    public static Runes getRuneNameFromRuneID(String id) {
        for (Runes rune : runes.values()) {
            if (rune.getName().equals(id)) {
                return rune;
            }
        }
        return null;
    }

    public static Runes getRuneFromName(String name) {
        for (Runes rune : runes.values()) {
            if (rune.getName().equals(name)) {
                return rune;
            }
        }
        return null;
    }

    public Runes getRuneFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getLore() != null) {
            for (String lore : meta.getLore()) {
                if (lore.startsWith(hex("&a✦ "))) {
                    String[] parts = lore.substring(4).split(" - ");
                    String runeName = parts[0];
                    System.out.println("Rune name: " + runeName);
                    return getRuneFromName(runeName);
                }
            }
        }
        return null;
    }

    public ItemStack createRuneItem(Runes rune) {
        ItemStack item = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(rune.getName());
            List<String> lore = new ArrayList<>();
            lore.add("Level: " + rune.getLevel());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static String getRuneIdByNameAndLevel(String name, int level) {
        for (Runes rune : runes.values()) {
            if (rune.getName().equalsIgnoreCase(name) && rune.getLevel() == level) {
                return rune.getId();
            }
        }
        return null; // Retourne null si aucune rune correspondante n'est trouvée
    }
}