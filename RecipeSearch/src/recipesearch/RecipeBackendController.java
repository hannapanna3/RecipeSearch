package recipesearch;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.Arrays;
import java.util.List;
public class RecipeBackendController {

    String cuisine;
    String mainIngredient;
    String difficulty;
    int maxPrice;
    int maxTime;

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    private SearchFilter searchFilter;

    private List<String> cuisines = Arrays.asList("Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
    private List<String> ingredients = Arrays.asList("Kött", "Fisk", "Kyckling", "Vegetarisk");
    private List<String> difficulties = Arrays.asList("Lätt", "Mellan", "Svår");
    private List<Integer> time = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150);

    public List<Recipe> getRecipes() {

        searchFilter = new SearchFilter(difficulty, maxTime, cuisine, maxPrice, mainIngredient);
        return db.search(searchFilter);

    }

    public void setCuisine(String cuisine){
        if (cuisine.contains(cuisine)){
            this.cuisine = cuisine;
        }else{
            this.cuisine = null;
        }
    }

    public void setMainIngredient(String mainIngredient){
        if (ingredients.contains(mainIngredient)){
            this.mainIngredient = mainIngredient;
        }else{
            this.mainIngredient = null;
        }
    }

    public void setDifficulty(String difficulty){
        if (difficulties.contains(difficulty)){
            this.difficulty = difficulty;
        }else{
            this.difficulty = null;
        }
    }

    public void setMaxPrice(int maxPrice){
        if(maxPrice > 0){
            this.maxPrice = maxPrice;
        }else{
            this.maxPrice = 0;
        }
    }

    public void setMaxTime(int maxTime){
        if(time.contains(maxTime)){
            this.maxTime = maxTime;
        }else{
            this.maxTime = 0;
        }
    }
}