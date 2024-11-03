package fr.cyberdodo.hopsAndGrapes.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.Plugin;

public class Rum extends AlcoholicBeverage {

    public Rum(Plugin plugin) {
        super(plugin, "Rhum", ChatColor.WHITE, Color.WHITE, "rum");
    }

    @Override
    public void applyEffects(Player player) {
        // Appliquer l'effet de nausée
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 60, 1)); // 60 secondes, niveau II
        // Appliquer l'effet de résistance au feu
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 0)); // 2 minutes
    }
}