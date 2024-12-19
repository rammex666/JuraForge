package fr.rammex.juraforge.craft;

import fr.rammex.juraforge.Juraforge;
import fr.rammex.juraforge.rune.RuneManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static fr.rammex.juraforge.rune.RuneSetup.runeManager;

public class PaternChecker {
    public static ItemStack checkPatern(ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slot7, ItemStack slot8, ItemStack slot9) {
        if (slot1.getType().equals(Material.AIR) && slot2.getType().equals(Material.AIR) && slot3.getType().equals(Material.AIR) && slot4.getType().equals(Material.AIR) && slot5.getType().equals(Material.AIR) && slot6.getType().equals(Material.AIR) && slot7.getType().equals(Material.AIR) && slot8.getType().equals(Material.AIR) && slot9.getType().equals(Material.AIR)) {
            return new ItemStack(Material.AIR);
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Aither")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Aither.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Armor")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Armor.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Aspis")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Aspis.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "FireResistance")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.FireResistance.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Force")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Force.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Ground")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Ground.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Life")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Life.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "NoDamageFall")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.NoDamageFall.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Pur")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Pur.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Speed")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Speed.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Vascel")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Vascel.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Vigueur")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Vigueur.runeName")));
        }

        if (matchesPattern(slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, "Watter")) {
            return runeManager.createRuneItem(RuneManager.getRune(Juraforge.instance.getConfig().getString("craft.Watter.runeName")));
        }

        return new ItemStack(Material.AIR);
    }

    private static boolean matchesPattern(ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slot7, ItemStack slot8, ItemStack slot9, String runeType) {
        return slot1.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot1")))
                && slot2.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot2")))
                && slot3.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot3")))
                && slot4.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot4")))
                && slot5.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot5")))
                && slot6.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot6")))
                && slot7.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot7")))
                && slot8.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot8")))
                && slot9.getType().equals(Material.matchMaterial(Juraforge.instance.getConfig().getString("craft." + runeType + ".slot9")));
    }
}