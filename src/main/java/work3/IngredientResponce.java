package work3;

import lombok.Data;

import java.util.List;


public class IngredientResponce {
    @Data
    public class IngredientResponse {

        private List<IngredientsById> ingredients;
    }
}