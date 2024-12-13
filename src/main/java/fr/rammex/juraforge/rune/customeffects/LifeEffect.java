package fr.rammex.juraforge.rune.customeffects;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LifeEffect implements CustomEffect {
    AuraSkillsApi auraSkills = AuraSkillsApi.get();

    @Override
    public void apply(Player player, int level) {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        double health = user.getStatLevel(Stats.HEALTH);
        user.addStatModifier(new StatModifier(player.getUniqueId()+"_HEALTH", Stats.HEALTH, health + 5*level));
        if(level == 5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1, true, false));
        }
    }

    @Override
    public void remove(Player player, int level) {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        user.removeStatModifier(player.getUniqueId()+"_HEALTH");
        if(level == 5){
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        }
    }


}
