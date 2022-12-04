package work4;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class ShoppingListTest extends ShoppingListSpecificationTest {

    private static final String hash = "5475f78358abb41d4f19c9c7fec7c3562968a38f";
    private static final String username = "qatesta2";
    private static final String date = "2022-12-04";
    private static final String startDate = "2022-12-04";
    private static final String endDate = "2022-12-05";

    private static String id;

    @Test
    @Disabled
    @DisplayName("Add Shopping List")
    void addShoppingList() throws Exception {
        String requestBody = getResource("addShoppingList.json");
        id = given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .pathParam("username", username)
                .body(requestBody)
                .when()
                .post("{username}/shopping-list/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();
    }

    @Test
    @Disabled
    @DisplayName("Get Shopping List Test")
    void getShoppingListTest() throws Exception {

        given()
                .queryParam("hash", hash)
                .pathParam("username", username)
                .expect()
                .when()
                .get("/{username}/shopping-list")
                .then()
                .statusCode(200);

    }

    @Test()
    @Disabled
    @DisplayName("Remove Coffee From Meal Plan")
    void removeCoffeeFromMealPlanTest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .pathParam("username", username)
                .when()
                .delete("{username}/shopping-list/items/" + id)
                .then()
                .body(containsString("success"))
                .statusCode(200);
    }
}
