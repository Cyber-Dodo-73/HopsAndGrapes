package fr.cyberdodo.hopsAndGrapes;

import fr.cyberdodo.hopsAndGrapes.cooking.CookingManager;
import fr.cyberdodo.hopsAndGrapes.crafting.CraftingManager;
import fr.cyberdodo.hopsAndGrapes.listeners.BrewingListener;
import fr.cyberdodo.hopsAndGrapes.listeners.DrinkListener;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;


public class HopsAndGrapesPlugin extends JavaPlugin {

    private CookingManager cookingManager;
    private CraftingManager craftingManager;

    @Override
    public void onEnable() {
        // Initialiser les gestionnaires
        cookingManager = new CookingManager(this);
        craftingManager = new CraftingManager(this);

        // Enregistrer les événements
        Bukkit.getPluginManager().registerEvents(new BrewingListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DrinkListener(this), this);

        // Enregistrer les recettes
        cookingManager.registerRecipes();
        craftingManager.registerRecipes();
    }
}