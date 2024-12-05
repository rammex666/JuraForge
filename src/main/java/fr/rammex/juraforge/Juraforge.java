package fr.rammex.juraforge;

import fr.rammex.juraforge.commands.RunesCommand;
import fr.rammex.juraforge.rune.RuneEffectListener;
import fr.rammex.juraforge.rune.RuneManager;
import fr.rammex.juraforge.rune.RuneMenu;
import fr.rammex.juraforge.rune.RuneSetup;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Juraforge extends JavaPlugin {

    @Getter
    private FileConfiguration messagesConf;
    private File file;

    @Override
    public void onEnable() {
        RuneManager runeManager = new RuneManager();
        this.getCommand("runes").setExecutor(new RunesCommand(runeManager));

        getServer().getPluginManager().registerEvents(new RuneMenu(this, runeManager), this);
        getServer().getPluginManager().registerEvents(new RuneEffectListener(runeManager), this);


        // Load yaml files
        loadFiles();
        saveDefaultConfig();
        RuneSetup runeSetup = new RuneSetup(this, runeManager);
        runeSetup.setupRunes();

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

    private void loadFiles() {
        loadFile("messages", null);
    }

    private void loadFile(String fileName, String folder) {
        File file;
        if (folder == null) {
            file = new File(getDataFolder(), fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource(fileName + ".yml", false);
            }
        } else {
            file = new File(getDataFolder() + "/" + folder, fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource(folder + "/" + fileName + ".yml", false);
            }
        }

        FileConfiguration fileConf = new YamlConfiguration();
        try {
            fileConf.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        switch (fileName) {
            case "messages":
                messagesConf = fileConf;
                break;
        }
    }
}
