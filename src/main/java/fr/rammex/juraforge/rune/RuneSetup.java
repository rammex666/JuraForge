package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.Juraforge;
import fr.rammex.juraforge.rune.customeffects.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuneSetup {
    public static JavaPlugin plugin;
    public static RuneManager runeManager;

    public RuneSetup(JavaPlugin plugin, RuneManager runeManager) {
        this.plugin = plugin;
        this.runeManager = runeManager;
    }

    public static void setupRunes() {
        FileConfiguration config = plugin.getConfig();
        for (String key : config.getConfigurationSection("runes").getKeys(false)) {
            String name = config.getString("runes." + key + ".name");
            List<Map<?, ?>> effectsList = config.getMapList("runes." + key + ".effects");
            List<CustomEffect> effects = new ArrayList<>();
            List<CustomEffect> onHoldItemEffects = new ArrayList<>();
            List<CustomEffect> onEquipEffects = new ArrayList<>();
            List<CustomEffect> onPlayerInteractEffects = new ArrayList<>();

            for (Map<?, ?> effectMap : effectsList) {
                String type = (String) effectMap.get("type");
                CustomEffect effect = null;

                switch (type) {
                    // types : Vascel, Speed, Life, Aither, Armor, Aspis, FireResistance, Force, Ground, NoDamageFall, Pur, Vigueur, Watter
                    case "Vascel":
                        effect = new VascelEffect();
                        plugin.getLogger().info("Vascel effect created");
                        break;
                    case "Speed":
                        effect = new SpeedEffect();
                        plugin.getLogger().info("Speed effect created");
                        break;
                    case "Aither":
                        effect = new AitherEffect();
                        plugin.getLogger().info("Aither effect created");
                        break;
                    case "Armor":
                        effect = new ArmorEffect();
                        plugin.getLogger().info("Armor effect created");
                        break;
                    case "Aspis":
                        effect = new AspisEffect();
                        plugin.getLogger().info("Aspis effect created");
                        break;
                    case "FireResistance":
                        effect = new FireResistanceEffect();
                        plugin.getLogger().info("FireResistance effect created");
                        break;
                    case "Force":
                        effect = new ForceEffect();
                        plugin.getLogger().info("Force effect created");
                        break;
                    case "Ground":
                        effect = new GroundEffect(Juraforge.instance);
                        plugin.getLogger().info("Ground effect created");
                        break;
                    case "NoDamageFall":
                        effect = new NoDamageFallEffect();
                        plugin.getLogger().info("NoDamageFall effect created");
                        break;
                    case "Pur":
                        effect = new PurEffect();
                        plugin.getLogger().info("Pur effect created");
                        break;
                    case "Vigueur":
                        effect = new VigueurEffect();
                        plugin.getLogger().info("Vigueur effect created");
                        break;
                    case "Watter":
                        effect = new WatterEffect();
                        plugin.getLogger().info("Watter effect created");
                        break;
                }


                if (effect != null) {
                    effects.add(effect);
                    if (effectMap.containsKey("onHoldItem") && (boolean) effectMap.get("onHoldItem")) {
                        onHoldItemEffects.add(effect);
                    }
                    if (effectMap.containsKey("onEquip") && (boolean) effectMap.get("onEquip")) {
                        onEquipEffects.add(effect);
                    }
                    if (effectMap.containsKey("onPlayerInteract") && (boolean) effectMap.get("onPlayerInteract")) {
                        onPlayerInteractEffects.add(effect);
                    }
                }
            }

            List<String> allowedItems = config.getStringList("runes." + key + ".allowed_items");
            int level = config.getInt("runes." + key + ".level");
            boolean isUpgradeable = config.getBoolean("runes." + key + ".is_upgradeable");

            Runes rune = new Runes(key, name, effects, allowedItems, level, isUpgradeable, onHoldItemEffects, onEquipEffects, onPlayerInteractEffects);
            runeManager.registerRune(rune);
            plugin.getLogger().info("Registered rune: " + key);
        }
    }
}