package fr.rammex.juraforge.commands;

import fr.rammex.juraforge.Juraforge;
import fr.rammex.juraforge.rune.RuneManager;
import fr.rammex.juraforge.rune.RuneMenu;
import fr.rammex.juraforge.rune.Runes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RunesCommand implements CommandExecutor {
    private final RuneManager runeManager;

    public RunesCommand(RuneManager runeManager) {
        this.runeManager = runeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            RuneMenu.openMenu(player);
            return true;
        }

        if(args[0].equals("reload")){
            if(!player.hasPermission("runes.reload")) {
                player.sendMessage("You do not have permission to use this command.");
                return false;
            }
            Juraforge.instance.reloadConfig();
            player.sendMessage("Runes reloaded.");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            if(!player.hasPermission("runes.give")) {
                player.sendMessage("You do not have permission to use this command.");
                return false;
            }
            String runeName = args[1];

            Runes rune = runeManager.getRune(runeName);
            if (rune != null) {
                ItemStack runeItem = runeManager.createRuneItem(rune);
                player.getInventory().addItem(runeItem);
                player.sendMessage("You have been given the rune: " + runeName);
            } else {
                player.sendMessage("Rune not found: " + runeName);
            }
            return true;
        }
        return false;
    }
}