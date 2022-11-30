package work3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.javacrumbs.jsonunit.JsonAssert;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class MealPlanTest {
    private static String id;
    private static final String API_KEY = "df76289e26bd474e8d8a3c52bc2baf74";
    private static final String hash = "5475f78358abb41d4f19c9c7fec7c3562968a38f";
    private static final String username = "qatesta2";
    private static final String date = "2022-11-30";


    @BeforeAll
    @Disabled
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/mealplanner/";
    }
    @BeforeEach
    @Disabled
    void AddCoffeeToMealPlan() {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .queryParam("apiKey", API_KEY)
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
                .queryParam("apiKey", API_KEY)
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
    void addCoffeeToMealPlanTest() {
        id = given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .queryParam("apiKey", API_KEY)
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
    void getMealPlanTest() throws IOException {

        String actually = given()
                .param("apiKey", API_KEY)
                .pathParam("username", username)
                .pathParam("date", "2022-05-22")
                .pathParam("hash", hash)
                .expect()
                .when()
                .get("/{username}/day/{date}?hash={hash}")
                .body()
                .prettyPrint();

        String expected = getResourceAsString("addExpected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actually
        );
    }

    private String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Test()
    @Disabled
    @DisplayName("Remove Coffee From Meal Plan")
    void removeCoffeeFromMealPlanTest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("hash", hash)
                .queryParam("apiKey", API_KEY)
                .pathParam("username", username)
                .when().log().all()
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
                .queryParam("apiKey", API_KEY)
                .pathParam("username", username)
                .pathParam("date", date)
                .when()
                .delete("{username}/day/{date}")
                .then()
                .body(containsString("message"))
                .body(containsString("success"))
                .statusCode(200);

    }
}