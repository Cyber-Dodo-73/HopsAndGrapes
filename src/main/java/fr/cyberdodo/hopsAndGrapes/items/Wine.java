package fr.cyberdodo.hopsAndGrapes.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.Plugin;

public class Wine extends AlcoholicBeverage {

    public Wine(Plugin plugin) {
        super(plugin, "Vin", ChatColor.DARK_RED, Color.MAROON, "wine");
    }

    @Override
    public void applyEffects(Player player) {
        // Appliquer l'effet de nausée
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 60, 1)); // 60 secondes, niveau II
        // Appliquer l'effet de régénération
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 60, 0)); // 1 minute, niveau I
    }
}