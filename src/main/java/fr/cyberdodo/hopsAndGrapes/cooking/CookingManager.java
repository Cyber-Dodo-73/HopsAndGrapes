package fr.cyberdodo.hopsAndGrapes.cooking;

import fr.cyberdodo.hopsAndGrapes.items.Beer;
import fr.cyberdodo.hopsAndGrapes.items.Rum;
import fr.cyberdodo.hopsAndGrapes.items.Vodka;
import fr.cyberdodo.hopsAndGrapes.items.Wine;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.Plugin;

public class CookingManager {

    private final Plugin plugin;

    public CookingManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerRecipes() {
        // Bière
        Beer beer = new Beer(plugin);
        FurnaceRecipe beerRecipe = new FurnaceRecipe(
                new NamespacedKey(plugin, "beer"),
                beer.createFinalDrink(),
                new RecipeChoice.ExactChoice(beer.createPreparation()),
                0.35f,
                200 // Temps de cuisson en ticks (10 secondes)
        );
        Bukkit.addRecipe(beerRecipe);

        // Vin
        Wine wine = new Wine(plugin);
        FurnaceRecipe wineRecipe = new FurnaceRecipe(
                new NamespacedKey(plugin, "wine"),
                wine.createFinalDrink(),
                new RecipeChoice.ExactChoice(wine.createPreparation()),
                0.35f,
                400 // Temps de cuisson en ticks (20 secondes)
        );
        Bukkit.addRecipe(wineRecipe);

        // Vodka
        Vodka vodka = new Vodka(plugin);
        FurnaceRecipe vodkaRecipe = new FurnaceRecipe(
                new NamespacedKey(plugin, "vodka"),
                vodka.createFinalDrink(),
                new RecipeChoice.ExactChoice(vodka.createPreparation()),
                0.35f,
                600 // Temps de cuisson en ticks (30 secondes)
        );
        Bukkit.addRecipe(vodkaRecipe);

        // Rhum
        Rum rum = new Rum(plugin);
        FurnaceRecipe rumRecipe = new FurnaceRecipe(
                new NamespacedKey(plugin, "rum"),
                rum.createFinalDrink(),
                new RecipeChoice.ExactChoice(rum.createPreparation()),
                0.35f,
                500 // Temps de cuisson en ticks (25 secondes)
        );
        Bukkit.addRecipe(rumRecipe);
    }
}