package fr.cyberdodo.hopsAndGrapes.crafting;

import fr.cyberdodo.hopsAndGrapes.items.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.NamespacedKey;

public class CraftingManager {

    private final Plugin plugin;

    public CraftingManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerRecipes() {
        // Recette pour la levure
        ItemStack yeast = CustomItems.createYeast(plugin);
        ShapelessRecipe yeastRecipe = new ShapelessRecipe(new NamespacedKey(plugin, "yeast"), yeast);
        yeastRecipe.addIngredient(Material.SUGAR);
        yeastRecipe.addIngredient(Material.ROTTEN_FLESH);
        Bukkit.addRecipe(yeastRecipe);

        // Recette pour le houblon
        ItemStack hops = CustomItems.createHops(plugin);
        ShapelessRecipe hopsRecipe = new ShapelessRecipe(new NamespacedKey(plugin, "hops"), hops);
        hopsRecipe.addIngredient(Material.WHEAT);
        Bukkit.addRecipe(hopsRecipe);

        // Recette pour le raisin
        ItemStack grapes = CustomItems.createGrapes(plugin);
        ShapelessRecipe grapesRecipe = new ShapelessRecipe(new NamespacedKey(plugin, "grapes"), grapes);
        grapesRecipe.addIngredient(Material.SWEET_BERRIES);
        Bukkit.addRecipe(grapesRecipe);

        // Recette pour la purée de pomme de terre
        ItemStack potatoMash = CustomItems.createPotatoMash(plugin);
        ShapelessRecipe potatoMashRecipe = new ShapelessRecipe(new NamespacedKey(plugin, "potato_mash"), potatoMash);
        potatoMashRecipe.addIngredient(Material.POTATO);
        Bukkit.addRecipe(potatoMashRecipe);

        // Recette pour la mélasse
        ItemStack molasses = CustomItems.createMolasses(plugin);
        ShapelessRecipe molassesRecipe = new ShapelessRecipe(new NamespacedKey(plugin, "molasses"), molasses);
        molassesRecipe.addIngredient(Material.SUGAR_CANE);
        Bukkit.addRecipe(molassesRecipe);
    }
}