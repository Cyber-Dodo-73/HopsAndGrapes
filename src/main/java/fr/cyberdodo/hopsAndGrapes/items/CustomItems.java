package fr.cyberdodo.hopsAndGrapes.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class CustomItems {

    public static ItemStack createYeast(Plugin plugin) {
        ItemStack item = new ItemStack(Material.FERMENTED_SPIDER_EYE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Levure");

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "yeast");

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createHops(Plugin plugin) {
        ItemStack item = new ItemStack(Material.NETHER_WART);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Houblon");

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "hops");

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGrapes(Plugin plugin) {
        ItemStack item = new ItemStack(Material.NETHER_WART);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Raisin");

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "grapes");

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createPotatoMash(Plugin plugin) {
        ItemStack item = new ItemStack(Material.NETHER_WART);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Purée de Pomme de Terre");

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "potato_mash");

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createMolasses(Plugin plugin) {
        ItemStack item = new ItemStack(Material.NETHER_WART);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Mélasse");

        // Définir les données personnalisées
        NamespacedKey key = new NamespacedKey(plugin, "hopsAndGrapes");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "molasses");

        item.setItemMeta(meta);
        return item;
    }
}