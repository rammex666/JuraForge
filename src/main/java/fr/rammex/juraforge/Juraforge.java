package fr.rammex.juraforge;

import fr.rammex.juraforge.commands.RunesCommand;
import fr.rammex.juraforge.rune.RuneEffectListener;
import fr.rammex.juraforge.rune.RuneMenu;
import fr.rammex.juraforge.rune.RuneSetup;
import fr.rammex.juraforge.rune.RuneManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Juraforge extends JavaPlugin {

    @Getter
    private FileConfiguration messagesConf;
    private File file;

    public static Juraforge instance;


    @Override
    public void onEnable() {
        instance = this;
        RuneManager runeManager = new RuneManager();
        this.getCommand("runes").setExecutor(new RunesCommand());

        Bukkit.getPluginManager().registerEvents((Listener) new RuneMenu(this, new RuneManager()), this);
        Bukkit.getPluginManager().registerEvents(new RuneEffectListener(runeManager), this);


        // setup the runes
        RuneSetup.setupRunes();

        // Load yaml files
        loadFiles();

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
        if(folder == null){
            File file = new File(getDataFolder(), fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource(fileName + ".yml", false);
            }
        } else {
            File file = new File(getDataFolder() + "/" + folder, fileName + ".yml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource(folder+"/"+fileName + ".yml", false);
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
