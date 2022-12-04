package work4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
public class RecipesComplexSearchTest extends RecipesSpecificationTest {

    @Test
    @Disabled
    void testNutritionById() {
        given()
                .pathParams("id", 1000000)
                .expect()
                .body("calories", is("362k"))
                .body("carbs", is("26g"))
                .body("fat", is("20g"))
                .body("protein", is("18g"))
                .body("expires", is(1654745351525L))
                .body("isStale", is(false))
                .when()
                .get("{id}/nutritionWidget.json");
    }

    @Test
    @Disabled
    void IngredientsByIdTest() {

        IngredientsById target = new IngredientsById("shredded-cheese-white.jpg", "shredded monterey jack cheese");

        IngredientResponce.IngredientResponse response = given()
                .pathParam("id", 1000000)
                .expect()
                .when()
                .get("{id}/ingredientWidget.json")
                .as(IngredientResponce.IngredientResponse.class);

        response.getIngredients()
                .stream()
                .filter(item -> item.getName().equals("shredded monterey jack cheese"))
                .peek(item -> Assertions.assertEquals(target, item))
                .findAny()
                .orElseThrow();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getIngredients());
        Assertions.assertEquals(15, response.getIngredients().size());

        IngredientsById ingredient1 = response.getIngredients().get(0);
        IngredientsById ingredient2 = response.getIngredients().get(1);
        IngredientsById ingredient3 = response.getIngredients().get(2);

        Assertions.assertEquals("chili powder", ingredient1.getName());
        Assertions.assertEquals("chili-powder.jpg", ingredient1.getImage());
        Assertions.assertEquals("cornstarch", ingredient2.getName());
        Assertions.assertEquals("white-powder.jpg", ingredient2.getImage());
        Assertions.assertEquals("flour tortillas", ingredient3.getName());
        Assertions.assertEquals("flour-tortilla.jpg", ingredient3.getImage());

    }

    @Test
    @Disabled
    void testConvertAmounts() {
        String sourceUnit = "cups";
        String targetUnit = "grams";
        Double sourceAmount = 2.5;
        Amounts amounts = given()
                .param("ingredientName", "flour")
                .param("sourceAmount", sourceAmount)
                .param("sourceUnit", sourceUnit)
                .param("targetUnit", targetUnit)
                .expect()
                .when()
                .get("convert")
                .as(Amounts.class);

        Assertions.assertNotNull(amounts);
        Assertions.assertEquals(sourceAmount, amounts.getSourceAmount());
        Assertions.assertEquals(sourceUnit, amounts.getSourceUnit());
        Assertions.assertEquals(312.5, amounts.getTargetAmount());
        Assertions.assertEquals(targetUnit, amounts.getTargetUnit());
        Assertions.assertTrue(amounts.getAnswer().contains("2.5 cups flour"));
        Assertions.assertTrue(amounts.getAnswer().contains("312.5 grams"));
    }


    @Test
    @Disabled
    void summaryItemTest() {
        SummaryItem response = given()
                .pathParams("id", 1231)
                .expect()
                .when()
                .get("{id}/summary")
                .as(SummaryItem.class);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getTitle());
        Assertions.assertNotNull(response.getSummary());
        Assertions.assertEquals(1231L, response.getId());
        Assertions.assertEquals("Grilled Fish Tacos", response.getTitle());
        Assertions.assertTrue(response.getTitle().startsWith("Grilled Fish"));
    }

    @Test
    @Disabled
    void analyzeRecipeOfSearchQueryTest() {
        Dish targetDish = new Dish("https://spoonacular.com/cdn/ingredients_100x100/rotisserie-chicken.png", "rotisserie-chicken");

        AnalyzeResponse response = given()
                .param("q", "grilled chicken with potato and apples and spices")
                .expect()
                .when()
                .get("queries/analyze")
                .as(AnalyzeResponse.class);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getIngredients());
        Assertions.assertNotNull(response.getDishes());
        Assertions.assertEquals(3, response.getIngredients().size());
        Assertions.assertEquals(1, response.getDishes().size());

        Ingredient ingredient1 = response.getIngredients().get(0);
        Ingredient ingredient2 = response.getIngredients().get(1);
        Ingredient ingredient3 = response.getIngredients().get(2);

        Assertions.assertEquals("spices", ingredient1.getName());
        Assertions.assertEquals(true, ingredient1.getInclude());
        Assertions.assertEquals("spices.png", ingredient1.getImage());

        Assertions.assertEquals("apple", ingredient2.getName());
        Assertions.assertEquals(true, ingredient2.getInclude());
        Assertions.assertEquals("apple.jpg", ingredient2.getImage());

        Assertions.assertEquals("potatoes yukon gold", ingredient3.getName());
        Assertions.assertEquals(true, ingredient3.getInclude());
        Assertions.assertEquals("potatoes-yukon-gold.png", ingredient3.getImage());

        response.getDishes()
                .stream()
                .filter(dish -> dish.getName().equals("rotisserie-chicken"))
                .peek(dish -> Assertions.assertEquals(targetDish, dish))
                .findAny()
                .orElseThrow();
    }
}
