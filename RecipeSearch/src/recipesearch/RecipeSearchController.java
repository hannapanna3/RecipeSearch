
package recipesearch;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.sun.webkit.dom.CSSValueImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.ait.dat215.lab2.Recipe;

import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {

    //RecipeDatabase db = RecipeDatabase.getSharedInstance();
    RecipeBackendController rbc;

    @FXML
    AnchorPane recipeDetailPane;
    @FXML
    StackPane searchPane;

    @FXML
    FlowPane hitList;

    @FXML
    ComboBox<String> mainIngredient;

    @FXML
    ComboBox<String> cuisine;

    @FXML
    RadioButton all;
    @FXML
    RadioButton easy;
    @FXML
    RadioButton middle;
    @FXML
    RadioButton hard;

    @FXML
    Spinner maxPrice;
    @FXML
    Slider maxTime;

    @FXML
    Label maxTimeLabel;

    @FXML
    Label recipeLabelDetail; //Detailed label
    @FXML
    ImageView recipeImgDetail; //detailed img

    @FXML
    Button close;


    private Map<String, RecipeListItem> recipeListItemMap = new HashMap<String, RecipeListItem>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbc = new RecipeBackendController();
        updateRecipeList();

        mainIngredient.getItems().addAll("Visa alla", "Kött", "Fisk", "Kyckling", "Vegetarisk");
        mainIngredient.getSelectionModel().select("Visa alla");

        mainIngredient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setMainIngredient(newValue);
                updateRecipeList();
            }


        });
        cuisine.getItems().addAll("Visa alla", "Sverige", "Grekland", "Indien", "Asien", "Afrika", "Frankrike");
        cuisine.getSelectionModel().select("Visa alla");

        cuisine.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setMainIngredient(newValue);
                updateRecipeList();
            }


        });


        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        all.setToggleGroup(difficultyToggleGroup);
        easy.setToggleGroup(difficultyToggleGroup);
        middle.setToggleGroup(difficultyToggleGroup);
        hard.setToggleGroup(difficultyToggleGroup);

        all.setSelected(true);


        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    rbc.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 300 , 100, 20);
        maxPrice.setValueFactory(valueFactory);
        maxPrice.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                maxPrice.focusedProperty().addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                        if(newValue){
                            //focusgained - do nothing
                        }
                        else{
                            Integer value = Integer.valueOf(maxPrice.getEditor().getText());
                            rbc.setMaxPrice(value);
                            updateRecipeList();
                        }

                    }
                });
            }

        });
        maxTime.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

                if(newValue != null && !newValue.equals(oldValue) && !maxTime.isValueChanging()) {
                    rbc.setMaxTime(newValue.intValue());
                    updateRecipeList();

                }
                maxTimeLabel.setText(String.valueOf((int)maxTime.getValue())+(String)" minuter");

            }
        });
        for (Recipe recipe : rbc.getRecipes()) {
            RecipeListItem recipeListItem = new RecipeListItem(recipe, this);
            recipeListItemMap.put(recipe.getName(), recipeListItem);
        }

    }
    @FXML
    public void closeRecipeView(){
        searchPane.toFront();

    }
    public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        recipeDetailPane.toFront();

    }
    private void populateRecipeDetailView(Recipe recipe){

        recipeLabelDetail.setText(recipe.getName());
        recipeImgDetail.setImage(recipe.getFXImage());
    }



    private void updateRecipeList(){
        hitList.getChildren().clear();
        for (Recipe r : rbc.getRecipes()) {
            hitList.getChildren().add(new RecipeListItem(r, this));
        }




    }
    

}