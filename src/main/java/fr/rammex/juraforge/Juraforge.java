package fr.rammex.juraforge;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Juraforge extends JavaPlugin {

    @Override
    public void onEnable() {

        // Check if AuraSkill plugin is installed
        checkAuraSkill();
    }

    @Override
    public void onDisable() {

        
    }

    public void checkAuraSkill() {
        Plugin auraSkill = getServer().getPluginManager().getPlugin("AuraSkills");
        if (auraSkill == null) {
            getLogger().severe("[☓] AuraSkill plugin pas installé! Désactivation de JuraForge...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("[✓] AuraSkill plugin trouvé! Juraforge activé.");
    }
}
