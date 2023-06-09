package recipesearch;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Text;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecipeListItem extends AnchorPane {
    private RecipeSearchController parentController;
    private Recipe recipe;

    @FXML
    Label recipeLabel;
    @FXML
    ImageView recipeImg;

    @FXML
    ImageView cuisineImage;
    @FXML
    ImageView logoImg;

    @FXML
    ImageView difficultyImg;

    @FXML
    Label labelTime;
    @FXML
    Label labelPrice;
    @FXML
    Text textRecipe;




    public RecipeListItem(Recipe recipe, RecipeSearchController recipeSearchController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipe_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.recipe = recipe;
        this.parentController = recipeSearchController;

        this.recipeLabel.setText(recipe.getName());
        this.recipeImg.setImage(parentController.getSquareImage(recipe.getFXImage()));
        this.labelTime.setText(String.valueOf(recipe.getTime())+ "minuter");
        this.labelPrice.setText(String.valueOf(recipe.getPrice())+ "kr");
        this.textRecipe.setText(recipe.getDescription());

        setCuisineImage();
        setMainIngredientImage();
        setDifficultyImage();

    }
    @FXML
    protected void onClick(Event event){
        parentController.openRecipeView(this.recipe);
    }
    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();


    protected void setCuisineImage(){
        Image imageFlag = parentController.getCuisineImage(recipe.getCuisine());
        this.cuisineImage.setImage(imageFlag);


    }

    protected void setMainIngredientImage(){
        Image imageMain = parentController.getMainIngredientImage(recipe.getMainIngredient());
        this.logoImg.setImage((imageMain));

    }

    protected void setDifficultyImage(){
        Image imageDiff = parentController.getDifficultyImage(recipe.getDifficulty());
        this.difficultyImg.setImage((imageDiff));
    }

}

