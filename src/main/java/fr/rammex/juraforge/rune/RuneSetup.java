package fr.rammex.juraforge.rune;

import fr.rammex.juraforge.rune.RuneManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Arrays;

public class RuneSetup {
    public static void setupRunes() {
        RuneManager runeManager = new RuneManager();

        Runes speedRune = new Runes(
                "Speed Rune",
                Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 600, 1)),
                Arrays.asList("DIAMOND_SWORD", "IRON_SWORD"),
                1
        );


        runeManager.registerRune(speedRune);

    }
}
