package work4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.*;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class MealPlanTest extends MealPlanSpecificationTest {
    private static final String hash = "5475f78358abb41d4f19c9c7fec7c3562968a38f";
    private static final String username = "qatesta2";
    private static final String date = "2022-12-04";

    private static String id;

    @BeforeEach
    @Disabled
    void AddCoffeeToMealPlan() {

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .pathParam("username", username)
                .body(" {\n"
                        + " \"date\": 1653264000,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + "\"name\": \"coffee\",\n"
                        + "\"unit\": \"cup\",\n"
                        + "\"amount\": \"1\",\n"
                        + "\"image\": \"https://spoonacular.com/cdn/ingredients_100x100/brewed-coffee.jpg\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("{username}/items");

        id = response.then().contentType(ContentType.JSON).extract().path("id").toString();
    }


    @Test
    @Disabled
    @DisplayName("Generate Meal Plan Test")
    void generateMealPlanTest() throws IOException {
        ValidatableResponse generate = given()
                .pathParam("timeFrame", date)
                .param("targetCalories", "500")
                .param("diet", "vegetarian")
                .param("exclude", "shellfish, olives")
                .expect()
                .when()
                .get("/generate?timeFrame={timeFrame}")
                .then()
                .statusCode(200);

    }


    @Test
    @Disabled
    @DisplayName("Add Coffee To Meal Plan Test")
    void addCoffeeToMealPlanTest() throws Exception {
        String requestBody = getResource("addCoffee.json");
        String response = given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .pathParam("username", username)
                .body(requestBody)
                .when()
                .post("{username}/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();
    }

    @Test
    @Disabled
    @DisplayName("Get Meal Plan Test")
    void getMealPlanTest() throws Exception {

        String actually = given()
                .pathParam("username", username)
                .pathParam("date", "2022-05-22")
                .pathParam("hash", hash)
                .expect()
                .when()
                .get("/{username}/day/{date}?hash={hash}")
                .body()
                .prettyPrint();

        String expected = getResource("expected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actually
        );
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
                .delete("{username}/items/" + id)
                .then()
                .body(containsString("success"))
                .statusCode(200);
    }

    @AfterEach
    @Disabled
    void tearDown() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .pathParam("username", username)
                .pathParam("date", date)
                .when()
                .delete("{username}/day/{date}")
                .then()
                .body(containsString("success"))
                .statusCode(200);

    }
}